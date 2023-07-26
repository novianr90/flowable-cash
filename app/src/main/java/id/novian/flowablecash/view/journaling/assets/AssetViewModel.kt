package id.novian.flowablecash.view.journaling.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
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

    fun processData(
        accountName: String,
        newBalance: AccountBalance
    ) {
        if (accountName == "Biaya Listrik" || accountName == "Biaya Air" || accountName == "Biaya Pembersihan" || accountName == "Biaya Promosi") {
            val balance = AccountBalance(
                debit = newBalance.debit,
                credit = 0
            )

            updateAdditionalAccount("Beban Operasional", balance)
        } else if (accountName == "Biaya Lainnya") {
            val balance = AccountBalance(
                debit = newBalance.debit,
                credit = 0
            )

            updateAdditionalAccount("Beban Lainnya", balance)
        }
    }

    private fun updateAdditionalAccount(accountName: String, balance: AccountBalance) {
        val disposable = postingUseCase.getAccounts(accountName, calendarHelper.getMonth())
            .flatMapCompletable { list ->
                val debit = list.balance.debit
                val credit = list.balance.credit

                val newBalance = AccountBalance(
                    debit = debit + balance.debit,
                    credit = credit + balance.credit
                )
                postingUseCase.updateAccountsToRemote(
                    accountName = accountName,
                    balance = newBalance,
                    month = calendarHelper.getMonth()
                )
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({
                // Nothing yet to implement
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
            })
        compositeDisposable.add(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}