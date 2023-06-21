package id.novian.flowablecash.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transaction: TransactionRepository,
    private val balanceSheet: BalanceSheetRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast
) : BaseViewModel() {

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
            .doOnSubscribe {
                _onLoading.postValue(true)
            }
            .subscribe({
                val sorted = it
                    .sortedBy { data -> data.accountNo }

                _dataBalanceSheet.postValue(sorted)
                _onResult.postValue(Result.SUCCESS)
                _onLoading.postValue(false)
            }, {
                it.printStackTrace()
                _onResult.postValue(Result.FAILED)
                _onLoading.postValue(false)
                errorMessage.postValue(it.message)
            })

        compositeDisposable.add(disposable)
    }

}