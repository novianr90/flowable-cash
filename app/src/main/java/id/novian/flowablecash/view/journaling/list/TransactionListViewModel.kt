package id.novian.flowablecash.view.journaling.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val toast: CreateToast,
    private val repo: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _dataTransactions: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataTransactions: LiveData<List<TransactionDomain>> get() = _dataTransactions

    private val _onError: MutableLiveData<Boolean> = MutableLiveData(false)
    val onError: LiveData<Boolean> get() = _onError

    private val _updateCondition: MutableLiveData<Result> = MutableLiveData()
    val updateCondition: LiveData<Result> get() = _updateCondition

    fun buttonTransactionClicked() {
        val disposable = repo.getAllTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ data ->
                _dataTransactions.postValue(data)
            }, {
                it.printStackTrace()
                _onError.postValue(true)
            })

        compositeDisposable.add(disposable)
    }

    fun buttonSaleClicked() {
        val disposable = repo.getAllSaleTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ data -> _dataTransactions.postValue(data) },
                {
                    it.printStackTrace()
                    _onError.postValue(true)
                })

        compositeDisposable.add(disposable)
    }

    fun buttonPurchaseClicked() {
        val disposable = repo.getAllPurchaseTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ data -> _dataTransactions.postValue(data) }, {
                it.printStackTrace()
                _onError.postValue(true)
            })

        compositeDisposable.add(disposable)
    }


    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun deleteTransaction(query: TransactionDomain) {
        val disposable = repo.deleteTransaction(query.id)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnComplete {
                _updateCondition.postValue(Result.SUCCESS)
            }
            .subscribe({
                // Nothing Implemented
            }, {
                _updateCondition.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}