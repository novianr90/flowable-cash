package id.novian.flowablecash.view.report.income_statements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CalendarHelper
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class IncomeStatementsViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val repo: TransactionRepository,
    val calendarHelper: CalendarHelper,
): BaseViewModel() {

    val totalPenjualan: MutableLiveData<Int> = MutableLiveData()

    val listOfHpp: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val totalHpp: MutableLiveData<Int> = MutableLiveData()

    val listOfBeban: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val totalBeban: MutableLiveData<Int> = MutableLiveData()

    private val _labaKotor: MutableLiveData<Int> = MutableLiveData()
    val labaKotor: LiveData<Int> get() = _labaKotor

    private val _labaBersih: MutableLiveData<Int> = MutableLiveData()
    val labaBersih: LiveData<Int> get() = _labaBersih

    private val hpp = listOf("Bahan Baku", "Bahan Tambahan", "Bahan Dagang")

    override fun viewModelInitialized() {
        getAllTransaction()
    }

    private fun getAllTransaction() {
        val disposable = repo.getAllTransactions()
            .doOnNext { (pemasukkan, pengeluaran) ->
                val penjualan = pemasukkan.sumOf { it.total }
                totalPenjualan.postValue(penjualan)

                val sortHpp = pengeluaran.filter { it.transactionName in hpp }
                val pembelian = sortHpp.sumOf { it.total }
                listOfHpp.postValue(sortHpp)
                totalHpp.postValue(pembelian)

                val sortBeban = pengeluaran
                    .filter { it.transactionName !in hpp }
                    .filter { it.transactionName != "Membayar Hutang" }
                val beban = sortBeban.sumOf { it.total }
                listOfBeban.postValue(sortBeban)
                totalBeban.postValue(beban)

                val countLabaKotor = penjualan - pembelian
                _labaKotor.postValue(countLabaKotor)

                val countLabaBersih = countLabaKotor - beban
                _labaBersih.postValue(countLabaBersih)
            }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({}, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }
}