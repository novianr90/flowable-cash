package id.novian.flowablecash.view.report.income_statements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.models.PurchasesJournal
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.domain.repository.PurchasesJournalRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class IncomeStatementsViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val cashReceiptRepo: CashReceiptJournalRepository,
    private val purchasesJournalRepo: PurchasesJournalRepository,
    private val accountRepo: AccountsRepository,
    val calendarHelper: CalendarHelper,
    private val postingUseCase: PostingUseCase
): BaseViewModel() {

    private val _cashReceipt: MutableLiveData<List<CashReceiptJournal>> = MutableLiveData()
    val cashReceipt: LiveData<List<CashReceiptJournal>> get() = _cashReceipt

    private val _purchasesJournal: MutableLiveData<List<PurchasesJournal>> = MutableLiveData()
    val purchasesJournal: LiveData<List<PurchasesJournal>> get() = _purchasesJournal

    private val _akunBebanPenjualan: MutableLiveData<List<AccountDomain>> = MutableLiveData()
    val akunBebanPenjualan: LiveData<List<AccountDomain>> get() = _akunBebanPenjualan

    private val _akunBebanPembelian: MutableLiveData<List<AccountDomain>> = MutableLiveData()
    val akunBebanPembelian: LiveData<List<AccountDomain>> get() = _akunBebanPembelian

    private val _labaKotor: MutableLiveData<Int> = MutableLiveData()
    val labaKotor: LiveData<Int> get() = _labaKotor

    private val _labaBersih: MutableLiveData<Int> = MutableLiveData()
    val labaBersih: LiveData<Int> get() = _labaBersih

    override fun viewModelInitialized() {
//        countAndMapData()
    }

    private val observableCashReceiptAndPurchasesJournal = Observable.combineLatest(
        cashReceiptRepo.getJournal().subscribeOn(schedulerIo),
        purchasesJournalRepo.getJournal().subscribeOn(schedulerIo),
        BiFunction { cash, purchase ->
            Pair(cash, purchase)
        }
    ).share()

    private val observableBebanPenjualanAndBebanPembelian = Maybe.zip(
        accountRepo.getAccountByAccountName("Beban Penjualan", calendarHelper.getMonth()).subscribeOn(schedulerIo),
        accountRepo.getAccountByAccountName("Beban Pembelian", calendarHelper.getMonth()).subscribeOn(schedulerIo),
        BiFunction { penjualan, pembelian ->
            Pair(penjualan, pembelian)
        }
    ).cache()

//    private fun countAndMapData() {
//        val disposable = Observable.combineLatest(
//            observableCashReceiptAndPurchasesJournal.subscribeOn(schedulerIo),
//            observableBebanPenjualanAndBebanPembelian.subscribeOn(schedulerIo),
//            BiFunction { (cash, purchase), (penjualan, pembelian) ->
//                FourData(cash, purchase, penjualan, pembelian)
//            }
//        )
//            .flatMap { (cash, purchase, penjualan, pembelian) ->
//                val filteredCash = cash.filter { calendarHelper.getMonthInList(it.date) == calendarHelper.getMonth() }
//                val filteredPurchases = purchase.filter { calendarHelper.getMonthInList(it.date) == calendarHelper.getMonth() }
//
//                _cashReceipt.postValue(filteredCash)
//                _purchasesJournal.postValue(filteredPurchases)
//                _akunBebanPenjualan.postValue(penjualan)
//                _akunBebanPembelian.postValue(pembelian)
//
//                Observable.just(FourData(filteredCash, filteredPurchases, penjualan, pembelian))
//            }
//            .subscribeOn(schedulerIo)
//            .observeOn(schedulerMain)
//            .subscribe({ (cash, purchase, penjualan, pembelian) ->
//                val totalCash = cash.sumOf { it.credit }
//                val totalPurchase = purchase.sumOf { it.debit }
//                val totalBebanPenjualan = penjualan.sumOf { it.balance.debit }
//                val totalBebanPembelian = pembelian.sumOf { it.balance.debit }
//
//                val labaKotor = totalCash - totalPurchase
//                val totalBeban = totalBebanPenjualan + totalBebanPembelian
//                val labaBersih = labaKotor - totalBeban
//
//                _labaKotor.postValue(labaKotor)
//                _labaBersih.postValue(labaBersih)
//            }, {
//                it.printStackTrace()
//                errorMessage.postValue(it.message)
//            })
//
//        compositeDisposable.add(disposable)
//    }
}