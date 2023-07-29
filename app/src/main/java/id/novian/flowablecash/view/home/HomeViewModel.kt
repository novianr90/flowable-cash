package id.novian.flowablecash.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transaction: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast,
    val calendarHelper: CalendarHelper,
) : BaseViewModel() {

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _onResult: MutableLiveData<Result> = MutableLiveData()
    val onResult: LiveData<Result> get() = _onResult

    private val _dataTransactions: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataTransactions: LiveData<List<TransactionDomain>> get() = _dataTransactions

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun getListOfTransactions() {
        val disposable = transaction.getAllTransactions()
            .doOnNext { (pemasukkan, pengeluaran) ->
                Log.i("HomeViewModel", "Pemasukkan is $pemasukkan")
                Log.i("HomeViewModel", "Pengeluaran is $pengeluaran")
                val sortedPemasukkan = pemasukkan.sortedBy { it.transactionDate }
                val sortedPengeluaran = pengeluaran.sortedBy { it.transactionDate }
                val allTransactions = sortedPemasukkan + sortedPengeluaran
                Log.i("HomeViewModel", "All Data is $allTransactions")
                _dataTransactions.postValue(allTransactions)
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({}, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

    fun deleteTransaction(query: TransactionDomain) {
        val disposable = transaction.deleteTransaction(query.id, query.transactionType)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnComplete {
                _onResult.postValue(Result.SUCCESS)
            }
            .subscribe({
                // Nothing Implemented
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _onResult.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }

}