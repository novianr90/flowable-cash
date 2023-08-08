package id.novian.flowablecash.view.report.rekap_hpp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TrialBalanceViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val repo: TransactionRepository,
    private val calendarHelper: CalendarHelper
): BaseViewModel() {

    private val _dataBalanceSheet: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataBalanceSheet: LiveData<List<TransactionDomain>> get() = _dataBalanceSheet

    private val _onProcess: MutableLiveData<Result> = MutableLiveData()
    val onProcess: LiveData<Result> get() = _onProcess

    private val listOfHpp = listOf("Bahan Baku", "Bahan Tambahan", "Barang Dagang")

    fun getJournal() {
        val disposable = repo.getAllTransactions()
            .flatMap { (_, pengeluaran) ->
                val filteredPengeluaran = pengeluaran
                    .filter { it.transactionName in listOfHpp }
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == calendarHelper.getMonth()
                    }
                Observable.just(filteredPengeluaran)
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe { _onProcess.postValue(Result.LOADING) }
            .subscribe({
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