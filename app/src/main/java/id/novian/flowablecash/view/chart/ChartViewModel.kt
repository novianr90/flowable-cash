package id.novian.flowablecash.view.chart

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.Function3
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repo: AccountsRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler
) : BaseViewModel() {
    var kasAccounts: MutableLiveData<List<AccountDomain>> = MutableLiveData()
        private set

    var penjualanAccounts: MutableLiveData<List<AccountDomain>> = MutableLiveData()
        private set

    var pembelianAccounts: MutableLiveData<List<AccountDomain>> = MutableLiveData()
        private set

    override fun viewModelInitialized() {
        getAllNeededAccounts()
    }

    private fun getAllNeededAccounts() {
        val disposable = Observable.zip(
            repo.getAllSpecific("Kas").subscribeOn(schedulerIo),
            repo.getAllSpecific("Penjualan").subscribeOn(schedulerIo),
            repo.getAllSpecific("Pembelian").subscribeOn(schedulerIo),
            Function3 { kas, penjualan, pembelian ->
                Triple(kas, penjualan, pembelian)
            }
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ (kas, penjualan, pembelian) ->
                kasAccounts.postValue(kas)
                penjualanAccounts.postValue(penjualan)
                pembelianAccounts.postValue(pembelian)
            }, { err ->
                err.printStackTrace()
                errorMessage.postValue(err.message)
            })

        compositeDisposable.add(disposable)
    }
}