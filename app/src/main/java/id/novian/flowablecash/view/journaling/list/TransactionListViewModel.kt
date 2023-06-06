package id.novian.flowablecash.view.journaling.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}