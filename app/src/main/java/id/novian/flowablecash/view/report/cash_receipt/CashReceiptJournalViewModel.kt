package id.novian.flowablecash.view.report.cash_receipt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.data.local.models.UpdateModelBalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.helpers.Constants
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CashReceiptJournalViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val repo: CashReceiptJournalRepository,
    private val toast: CreateToast,
    private val postingUseCase: PostingUseCase,
    private val gson: Gson
) : BaseViewModel() {
    private val _dataCashReceiptJournal: MutableLiveData<List<CashReceiptJournal>> =
        MutableLiveData()
    val dataCashReceiptJournal: LiveData<List<CashReceiptJournal>> get() = _dataCashReceiptJournal

    private val _invocationFlag: MutableLiveData<Boolean> = MutableLiveData()
    val invocationFlag: LiveData<Boolean> get() = _invocationFlag

    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result> get() = _result

    fun getCashReceiptJournal() {
        val disposable = repo.getJournal()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe { _result.postValue(Result.LOADING) }
            .doAfterNext {
                val debit = it.sumOf { data -> data.debit }
                val credit = it.sumOf { data -> data.credit }

                val newQuery = UpdateModelBalanceSheet(
                    id = null,
                    accountNameDebit = "Kas",
                    accountNameCredit = "Penjualan",
                    debit = debit,
                    credit = credit,
                    alreadyUpdated = 0
                )

                postingUseCase.insertUpdateBalanceSheetToStorage(newQuery)
                    .subscribeOn(schedulerIo)
                    .subscribe()



            }
            .subscribe({
                _result.postValue(Result.SUCCESS)
                _dataCashReceiptJournal.postValue(it)
            }, {
                it.printStackTrace()
                _result.postValue(Result.FAILED)
                errorMessage.postValue(it.message)
            })

        val disposable2 = Completable.fromAction {
            updateBalanceSheetRemoteOnce()
        }
            .subscribeOn(schedulerIo)
            .subscribe()

        compositeDisposable.addAll(disposable, disposable2)
    }

    private fun updateBalanceSheetToRemote() {
        val disposable = Observable.zip(
            postingUseCase.getBalanceSheetCurrentOnRemote("Kas").subscribeOn(schedulerIo),
            postingUseCase.getBalanceSheetCurrentOnRemote("Penjualan").subscribeOn(schedulerIo),
            BiFunction { kas: BalanceSheetDomain, penjualan: BalanceSheetDomain ->
                Pair(kas, penjualan)
            }
        )
            .flatMap { (kas, penjualan) ->
                val currentBalanceSheet = UpdateModelBalanceSheet(
                    id = null,
                    accountNameDebit = "Kas",
                    accountNameCredit = "Penjualan",
                    debit = kas.balance.debit,
                    credit = penjualan.balance.credit,
                    alreadyUpdated = 2
                )
                Log.d("NewBalanceSheet", "data on 1st level is $currentBalanceSheet")
                Observable.just(currentBalanceSheet)
            }
            .flatMap { remote ->
                Observable.zip(
                    postingUseCase.getUpdateModelBalanceSheetDebit("Kas").subscribeOn(schedulerIo),
                    postingUseCase.getUpdateBalanceSheetCredit("Penjualan").subscribeOn(schedulerIo),
                    BiFunction { kas, penjualan ->
                        Pair(kas, penjualan)
                    }
                )
                    .flatMap { (localKas, localPenjualan) ->

                        val remoteData = UpdateModelBalanceSheet(
                            id = null,
                            accountNameCredit = "Penjualan",
                            accountNameDebit = "Kas",
                            debit = remote.debit,
                            credit = remote.credit,
                            alreadyUpdated = 2
                        )

                        val new = UpdateModelBalanceSheet(
                            id = null,
                            accountNameDebit = "Kas",
                            accountNameCredit = "Penjualan",
                            debit = remote.debit + localKas.debit,
                            credit = remote.credit + localPenjualan.credit,
                            alreadyUpdated = localKas.alreadyUpdated
                        )
                        Log.d("NewBalanceSheet", "data new is $new")
                        Log.d("NewBalanceSheet", "data remote is $remoteData")

                        Observable.just(Pair(remoteData, new))
                    }
            }
            .map { (remoteData, newData) ->
                postingUseCase.setUpdateBalanceSheet("Kas", "Penjualan")
                val balanceKas = AccountBalance(
                    debit = newData.debit - remoteData.debit,
                    credit = 0
                )
                val balancePenjualan = AccountBalance(
                    debit = 0,
                    credit = newData.credit - remoteData.credit
                )
                Log.d("NewBalanceSheet", "balancekas is $balanceKas")
                Log.d("NewBalanceSheet", "balancepenjualan is $balancePenjualan")

                postingUseCase.setUpdateBalanceSheet("Kas", "Penjualan")
                    .subscribeOn(schedulerIo)
                    .subscribe()

                postingUseCase.updateBalanceSheetToRemote("Kas", balanceKas)
                    .subscribeOn(schedulerIo)
                    .subscribe()
                postingUseCase.updateBalanceSheetToRemote("Penjualan", balancePenjualan)
                    .subscribeOn(schedulerIo)
                    .subscribe()
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .take(1)
            .subscribe({
                       _result.postValue(Result.SUCCESS)
            },{
                it.printStackTrace()
                errorMessage.postValue(it.message)
            })

        compositeDisposable.addAll(disposable)
    }

    private fun updateBalanceSheetRemoteOnce() {
        if (!Constants.isSentBalanceSheet) {
            updateBalanceSheetToRemote()
            Constants.isSentBalanceSheet = true
        }
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}