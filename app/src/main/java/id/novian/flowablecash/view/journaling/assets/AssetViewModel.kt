package id.novian.flowablecash.view.journaling.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
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
    private val calendarHelper: CalendarHelper,
) : BaseViewModel() {
    private val _onSuccess: MutableLiveData<Result> = MutableLiveData()
    val onSuccess: LiveData<Result> get() = _onSuccess

    fun processData(
        accountName: String,
        newBalance: AccountBalance,
        payment: String
    ) {
        when (accountName) {
            "Uang Tunai dan Saldo Bank" -> {
                val newKas = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Kas", newKas)


                val newModal = AccountBalance(
                    debit = 0,
                    credit = newBalance.credit
                )
                updateAdditionalAccount("Modal", newModal)
            }

            "Piutang Usaha" -> {
                val newPiutang = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Piutang Dagang", newPiutang)

                val newModal = AccountBalance(
                    debit = 0,
                    credit = newBalance.credit
                )
                updateAdditionalAccount("Modal", newModal)
            }

            "Stok Barang" -> {

                val newBarang = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Barang Dagang", newBarang)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }

            "Stok Bahan Baku" -> {
                val newBahanBaku = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Bahan Baku", newBahanBaku)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }

            "Stok Bahan Tambahan" -> {
                val newTambahan = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Bahan Tambahan", newTambahan)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }

            "Peralatan Bisnis" -> {
                val newPeralatan = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Peralatan", newPeralatan)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }

            "Kendaraan" -> {
                val newKendaraan = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Kendaraan", newKendaraan)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }

            "Peralatan Lainnya" -> {
                val newPeralatan = AccountBalance(
                    debit = newBalance.debit,
                    credit = 0
                )
                updateAdditionalAccount("Peralatan", newPeralatan)

                if (payment == "Tunai") {
                    val newModal = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Modal", newModal)
                } else {
                    val newHutang = AccountBalance(
                        debit = 0,
                        credit = newBalance.credit
                    )
                    updateAdditionalAccount("Hutang Dagang", newHutang)
                }
            }
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
            .doOnComplete { _onSuccess.postValue(Result.SUCCESS) }
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