package id.novian.flowablecash.view.report.financial_position

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.helpers.CalendarHelper
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FinancialPositionViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val accountRepo: AccountsRepository,
    private val calendarHelper: CalendarHelper
): BaseViewModel() {

    private val _dataAllAccounts: MutableLiveData<List<AccountDomain>> = MutableLiveData()
    val dataAllAccounts: LiveData<List<AccountDomain>> get() = _dataAllAccounts

    private val observableAccounts = accountRepo.getAllAccounts(calendarHelper.getMonth())
        .cache()

    override fun viewModelInitialized() {
        getAllAccounts()
    }

    private fun getAllAccounts() {
        val disposable = observableAccounts
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({
                _dataAllAccounts.postValue(it)
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
            })

        compositeDisposable.add(disposable)
    }

}