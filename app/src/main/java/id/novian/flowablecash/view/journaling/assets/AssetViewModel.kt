package id.novian.flowablecash.view.journaling.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AssetViewModel @Inject constructor(
    private val repo: BalanceSheetRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast
) : BaseViewModel() {
    private val _onSuccess: MutableLiveData<Result> = MutableLiveData()
    val onSuccess: LiveData<Result> get() = _onSuccess

    fun updateBalance(
        accountName: String,
        newBalance: AccountBalance,
    ) {
        val disposable = repo.getBalanceSheetByAccountName(accountName)
            .flatMap {
                val balance = AccountBalance(
                    debit = it.balance.debit + newBalance.debit,
                    credit = it.balance.credit + newBalance.credit
                )
                repo.updateBalanceSheet(
                    accountName = accountName,
                    balance = balance
                )
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onSuccess.postValue(Result.LOADING)
            }
            .subscribe({
                _onSuccess.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                _onSuccess.postValue(Result.FAILED)
                errorMessage.postValue(it.message)
            })

        compositeDisposable.add(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}