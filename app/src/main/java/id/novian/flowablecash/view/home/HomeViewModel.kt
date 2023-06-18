package id.novian.flowablecash.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transaction: TransactionRepository,
    private val balanceSheet: BalanceSheetRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _onResult: MutableLiveData<Result> = MutableLiveData()
    val onResult: LiveData<Result> get() = _onResult

    private val _dataBalanceSheet: MutableLiveData<List<BalanceSheetDomain>> = MutableLiveData()
    val dataBalanceSheet: LiveData<List<BalanceSheetDomain>> = _dataBalanceSheet

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun getBalanceSheet() {
        val disposable = balanceSheet.getBalanceSheet()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({
                _dataBalanceSheet.postValue(it)
                _onResult.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                _onResult.postValue(Result.FAILED)
                createToast(it.message ?: "Error Occurred!")
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}