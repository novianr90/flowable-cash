package id.novian.flowablecash.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.domain.repository.PurchasesJournalRepository
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Scheduler
import java.util.Calendar
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
    private val cashRepo: CashReceiptJournalRepository,
    private val purchaseRepo: PurchasesJournalRepository,
    private val calendar: Calendar,
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {

    private val getMonth = calendar.get(Calendar.MONTH) + 1

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _onResult: MutableLiveData<Result> = MutableLiveData()
    val onResult: LiveData<Result> get() = _onResult

    private val _dataTransactions: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataTransactions: LiveData<List<TransactionDomain>> get() = _dataTransactions

    private val observableTransaction = transaction.getAllTransactions().cache()

    override fun viewModelInitialized() {

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
                _onResult.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
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
}