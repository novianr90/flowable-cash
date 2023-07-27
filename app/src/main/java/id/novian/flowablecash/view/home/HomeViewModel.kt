package id.novian.flowablecash.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transaction: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast,
    val calendarHelper: CalendarHelper,
    private val postingUseCase: PostingUseCase,
) : BaseViewModel() {

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _onResult: MutableLiveData<Result> = MutableLiveData()
    val onResult: LiveData<Result> get() = _onResult

    private val _dataTransactions: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataTransactions: LiveData<List<TransactionDomain>> get() = _dataTransactions

    private val observableTransaction = transaction.getAllTransactions().cache()

    override fun viewModelInitialized() {
        postingBebanBeban()
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun getListOfTransactions() {
        val disposable = observableTransaction
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ data ->
                val sorted = data.sortedBy { it.transactionDate }
                _dataTransactions.postValue(sorted)
            }, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

    fun deleteTransaction(query: TransactionDomain) {
        val disposable = transaction.deleteTransaction(query.id)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnComplete {
                _onResult.postValue(Result.SUCCESS)
            }
            .subscribe({
                // Nothing Implemented
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _onResult.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }

    private fun postingBebanBeban() {
        val disposable = observableTransaction
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .flatMapCompletable { list ->
                val biayaOngkosKirim = list.filter { it.feeType == "Biaya Ongkos Kirim" }
                val biayaLainnya = list.filter { it.feeType == "Biaya Lainnya" }
                val biayaPengemasan = list.filter { it.feeType == "Biaya Pengemasan" }

                val ongkosBalance = AccountBalance(
                    debit = biayaOngkosKirim.sumOf { it.fee },
                    credit = 0
                )

                val lainnyaBalance = AccountBalance(
                    debit = biayaLainnya.sumOf { it.fee },
                    credit = 0
                )

                val pengemasanBalance = AccountBalance(
                    debit = biayaPengemasan.sumOf { it.fee },
                    credit = 0
                )

                postingUseCase.updateAccountsToRemote(
                    "Beban Ongkos",
                    ongkosBalance,
                    calendarHelper.getMonth()
                )
                    .andThen(
                        postingUseCase.updateAccountsToRemote(
                            "Beban Lainnya",
                            lainnyaBalance,
                            calendarHelper.getMonth()
                        )
                    )
                    .andThen(
                        postingUseCase.updateAccountsToRemote(
                            "Beban Pengemasan",
                            pengemasanBalance,
                            calendarHelper.getMonth()
                        )
                    )
            }
            .subscribe({
                // Not yet implemented
            }, { err ->
                err.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }
}