package id.novian.flowablecash.view.report.balance_sheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TrialBalanceViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val repo: AccountsRepository,
    private val calendarHelper: CalendarHelper
): BaseViewModel() {

    private val _dataBalanceSheet: MutableLiveData<List<AccountDomain>> = MutableLiveData()
    val dataBalanceSheet: LiveData<List<AccountDomain>> get() = _dataBalanceSheet

    private val _onProcess: MutableLiveData<Result> = MutableLiveData()
    val onProcess: LiveData<Result> get() = _onProcess

    fun getJournal() {
        val disposable = repo.getAllAccounts(calendarHelper.getMonth())
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe { _onProcess.postValue(Result.LOADING) }
            .subscribe({
                Log.d("BalanceSheetData", "data is $it")
              _dataBalanceSheet.postValue(it)
              _onProcess.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _onProcess.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }
}