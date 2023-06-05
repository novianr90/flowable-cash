package id.novian.flowablecash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.data.remote.models.purchase.Purchase
import id.novian.flowablecash.data.remote.models.sale.Sale
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.repository.PurchaseRemoteRepository
import id.novian.flowablecash.data.remote.repository.SaleRemoteRepository
import id.novian.flowablecash.data.remote.repository.TransactionRemoteRepository
import id.novian.flowablecash.domain.models.PurchaseDomain
import id.novian.flowablecash.domain.models.SaleDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactions: TransactionRemoteRepository,
    private val sales: SaleRemoteRepository,
    private val purchases: PurchaseRemoteRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    @Named("TRANSACTION_MAPPER") private val transactionMapper: Mapper<Transaction, TransactionDomain>,
    @Named("SALE_MAPPER") private val saleMapper: Mapper<Sale, SaleDomain>,
    @Named("PURCHASE_MAPPER") private val purchaseMapper: Mapper<Purchase, PurchaseDomain>,
    private val toast: CreateToast
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _onError: MutableLiveData<Boolean> = MutableLiveData(false)
    val onError: LiveData<Boolean> get() = _onError

    private val _dataTransactions: MutableLiveData<List<TransactionDomain>> = MutableLiveData()
    val dataTransactions: LiveData<List<TransactionDomain>> get() = _dataTransactions

    private val _dataSales: MutableLiveData<List<SaleDomain>> = MutableLiveData()
    val dataSales: LiveData<List<SaleDomain>> get() = _dataSales

    private val _dataPurchase: MutableLiveData<List<PurchaseDomain>> = MutableLiveData()
    val dataPurchase: LiveData<List<PurchaseDomain>> get() = _dataPurchase

    fun buttonTransactionsClicked() {
        getAllTransactions()
    }

    private fun getAllTransactions() {
        val disposable = transactions.getTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onLoading.postValue(true)
            }
            .doOnNext {
                _onError.postValue(false)
                val domain = it.transaction.map { trx -> transactionMapper.mapToDomain(trx) }

                _dataTransactions.postValue(domain)
            }
            .doOnComplete {
                _onLoading.postValue(false)
            }
            .doOnError {
                it.printStackTrace()
                _onError.postValue(true)
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun buttonSaleClicked() {
        getAllSaleTransaction()
    }

    private fun getAllSaleTransaction() {
        val disposable = sales.getAllSaleTypeTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onLoading.postValue(true)
            }
            .doOnNext {
                _onError.postValue(false)

                val domain = it.sale.map { sale -> saleMapper.mapToDomain(sale) }

                _dataSales.postValue(domain)
            }
            .doOnError {
                it.printStackTrace()
                _onError.postValue(true)
            }
            .doOnComplete {
                _onLoading.postValue(false)
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun buttonPurchaseClicked() {
        getAllPurchaseTransactions()
    }

    private fun getAllPurchaseTransactions() {
        val disposable = purchases.getAllPurchaseTypeTransactions()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onLoading.postValue(true)
            }
            .doOnNext {
                _onError.postValue(false)

                val domain = it.purchase.map { purchase -> purchaseMapper.mapToDomain(purchase) }

                _dataPurchase.postValue(domain)
            }
            .doOnError {
                _onError.postValue(true)
                it.printStackTrace()
            }
            .doOnComplete {
                _onLoading.postValue(false)
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}