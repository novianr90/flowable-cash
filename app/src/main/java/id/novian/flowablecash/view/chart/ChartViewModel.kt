package id.novian.flowablecash.view.chart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repo: AccountsRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val transactions: TransactionRepository,
    val calendarHelper: CalendarHelper
) : BaseViewModel() {
    var kasAccounts: MutableLiveData<List<AccountDomain>> = MutableLiveData()
        private set

    var pemasukkanData: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
        private set

    var pengeluaranData: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
        private set

    override fun viewModelInitialized() {
        getAllNeededAccounts()
        getAllTransactions()
    }

    private fun getAllNeededAccounts() {
        val disposable = repo.getAllSpecific("Kas").subscribeOn(schedulerIo)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ kas ->
                kasAccounts.postValue(kas)
            }, { err ->
                err.printStackTrace()
                errorMessage.postValue(err.message)
            })

        compositeDisposable.add(disposable)
    }

    private fun getAllTransactions() {
        val disposable = transactions.getAllTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ (pemasukkan, pengeluaran) ->
                Log.i("Chart", "Pemasukkan: $pemasukkan")
                Log.i("Chart", "Pengeluaran: $pengeluaran")
                pemasukkanData.postValue(pemasukkan)
                pengeluaranData.postValue(pengeluaran)
            }, { err ->
                err.printStackTrace()
                errorMessage.postValue(err.message)
            })

        compositeDisposable.add(disposable)
    }
}