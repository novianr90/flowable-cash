package id.novian.flowablecash.view.report.income_statements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.FourData
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function4
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class IncomeStatementsViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val accountRepo: AccountsRepository,
    val calendarHelper: CalendarHelper,
): BaseViewModel() {

    var totalPenjualan: MutableLiveData<Int> = MutableLiveData()
        private set

    var totalPembelian: MutableLiveData<Int> = MutableLiveData()
        private set

    var akunBebanOngkos: MutableLiveData<Int> = MutableLiveData()
        private set

    var akunBebanPengemasan: MutableLiveData<Int> = MutableLiveData()
        private set

    var akunBebanOperasional: MutableLiveData<Int> = MutableLiveData()
        private set

    var akunBebanLainnya: MutableLiveData<Int> = MutableLiveData()
        private set

    private val _labaKotor: MutableLiveData<Int> = MutableLiveData()
    val labaKotor: LiveData<Int> get() = _labaKotor

    private val _labaBersih: MutableLiveData<Int> = MutableLiveData()
    val labaBersih: LiveData<Int> get() = _labaBersih

    override fun viewModelInitialized() {
        countAndMapData()
    }

    private val observableCashReceiptAndPurchasesJournal = Maybe.zip(
        accountRepo.getAccountByAccountName("Penjualan", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        accountRepo.getAccountByAccountName("Pembelian", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        BiFunction { cash, purchase ->
            Pair(cash, purchase)
        }
    ).cache()

    private val maybeBebanBeban = Maybe.zip(
        accountRepo.getAccountByAccountName("Beban Ongkos", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        accountRepo.getAccountByAccountName("Beban Pengemasan", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        accountRepo.getAccountByAccountName("Beban Lainnya", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        accountRepo.getAccountByAccountName("Beban Operasional", calendarHelper.getMonth())
            .subscribeOn(schedulerIo),
        Function4 { ongkos, pengemasan, lainnya, operasional ->
            FourData(ongkos, pengemasan, lainnya, operasional)
        }
    ).cache()

    private fun countAndMapData() {
        val disposable = Maybe.zip(
            observableCashReceiptAndPurchasesJournal,
            maybeBebanBeban,
            BiFunction { cashPurchasePair, bebanBebanPair ->
                Pair(cashPurchasePair, bebanBebanPair)
            }
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({ (cashPurchasePair, bebanBeban) ->

                val penjualan = cashPurchasePair.first
                val pembelian = cashPurchasePair.second

                val ongkos = bebanBeban.first
                val pengemasan = bebanBeban.second
                val lainnya = bebanBeban.third
                val operasional = bebanBeban.fourth

                val totalCash = penjualan.balance.credit
                val totalPurchase = pembelian.balance.debit

                val bebanOngkos = ongkos.balance.debit
                val bebanPengemasan = pengemasan.balance.debit
                val bebanLainnya = lainnya.balance.debit
                val bebanOperasional = operasional.balance.debit

                val labaKotor = totalCash - totalPurchase
                val totalBeban = bebanOngkos + bebanPengemasan + bebanLainnya + bebanOperasional
                val labaBersih = labaKotor - totalBeban

                totalPenjualan.postValue(totalCash)
                totalPembelian.postValue(totalPurchase)

                akunBebanOngkos.postValue(bebanOngkos)
                akunBebanPengemasan.postValue(bebanPengemasan)
                akunBebanLainnya.postValue(bebanLainnya)
                akunBebanOperasional.postValue(bebanOperasional)

                // Laba
                _labaKotor.postValue(labaKotor)
                _labaBersih.postValue(labaBersih)
            }, { err ->
                err.printStackTrace()
                errorMessage.postValue(err.message)
            })

        compositeDisposable.add(disposable)
    }
}