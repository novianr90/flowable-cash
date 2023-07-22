package id.novian.flowablecash.view.journaling.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AssetViewModel @Inject constructor(
    private val postingUseCase: PostingUseCase,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast,
    private val calendarHelper: CalendarHelper
) : BaseViewModel() {
    private val _onSuccess: MutableLiveData<Result> = MutableLiveData()
    val onSuccess: LiveData<Result> get() = _onSuccess

//    fun processData(
//        accountName: String,
//        newBalance: AccountBalance
//    ) {
//        when(accountName) {
//            "Menambah Modal" -> updateAccount(newBalance)
//            "Mengambil Laba" -> {
//                val affectedBalanceOnPrive = AccountBalance(
//                    debit = newBalance.debit,
//                    credit = 0
//                )
//                updateAdditionalAccount("Mengambil Laba", affectedBalanceOnPrive)
//
//                val affectedBalanceKas = AccountBalance(
//                    debit = - newBalance.debit,
//                    credit = 0
//                )
//                updateAdditionalAccount("Kas", affectedBalanceKas)
//            }
//        }
//    }

//    private fun updateAccount(
//        balance: AccountBalance
//    ) {
//        Log.d("Func", "Invoked!")
//        val disposable = Observable.combineLatest(
//            postingUseCase.getAccounts("Kas", calendarHelper.getMonth()).subscribeOn(schedulerIo),
//            postingUseCase.getAccounts("Modal", calendarHelper.getMonth()).subscribeOn(schedulerIo),
//            BiFunction { debitAcc, creditAcc ->
//                Pair(debitAcc, creditAcc)
//            }
//        )
//            .flatMapCompletable { (debitAcc, creditAcc) ->
//                val debitAccDebitBalance = debitAcc.sumOf { it.balance.debit }
//                val debitAccCreditBalance = debitAcc.sumOf { it.balance.credit }
//
//                val creditAccDebitBalance = creditAcc.sumOf { it.balance.debit }
//                val creditAccCreditBalance = creditAcc.sumOf { it.balance.credit }
//
//                val newUpdateDebitAccount = AccountBalance(
//                    debit = debitAccDebitBalance + balance.debit,
//                    credit = debitAccCreditBalance
//                )
//
//                val newUpdateCreditAccount = AccountBalance(
//                    debit = creditAccDebitBalance,
//                    credit = creditAccCreditBalance + balance.credit
//                )
//
//                postingUseCase.updateAccountsToRemote("Kas", newUpdateDebitAccount, calendarHelper.getMonth())
//                    .andThen(postingUseCase.updateAccountsToRemote("Modal", newUpdateCreditAccount, calendarHelper.getMonth()))
//            }
//            .doOnSubscribe { Log.d("Update2Data", "Invoked!") }
//            .subscribeOn(schedulerIo)
//            .observeOn(schedulerMain)
//            .subscribe({
//                // Nothing yet to implement
//                       Log.d("AssetViewModel", "Subscribed!")
//            }, {
//                it.printStackTrace()
//                errorMessage.postValue(it.message)
//                Log.d("AssetViewModel", "Error! ${it.message}")
//            })
//
//        compositeDisposable.add(disposable)
//    }

//    private fun updateAdditionalAccount(accountName: String, balance: AccountBalance) {
//        val disposable = postingUseCase.getAccounts(accountName, calendarHelper.getMonth())
//            .flatMapCompletable { list ->
//                val debit = list.sumOf { it.balance.debit }
//                val credit = list.sumOf { it.balance.credit }
//
//                val newBalance = AccountBalance(
//                    debit = debit + balance.debit,
//                    credit = credit + balance.credit
//                )
//                postingUseCase.updateAccountsToRemote(accountName = accountName, balance = newBalance, month = calendarHelper.getMonth())
//            }
//            .subscribeOn(schedulerIo)
//            .observeOn(schedulerMain)
//            .subscribe({
//                Log.d("AssetViewModel", "Subscribed!")
//            }, {
//                it.printStackTrace()
//                errorMessage.postValue(it.message)
//                Log.d("AssetViewModel", "Error! ${it.message}")
//            })
//        compositeDisposable.add(disposable)
//    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}