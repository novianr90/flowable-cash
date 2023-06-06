package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface MainRemoteRepository {
    // Purchase
    fun getAllPurchaseTypeTransactions(): Observable<Transactions>
    fun getPurchaseTypeTransaction(id: Int): Observable<Transaction>

    // Sale
    fun getAllSaleTypeTransactions(): Observable<Transactions>
    fun getSaleTypeTransaction(id: Int): Observable<Transaction>

    // Transaction
    fun getTransactions(): Observable<Transactions>
    fun getTransactionById(id: Int): Observable<Transaction>

    fun postTransaction(
        name: String,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Observable<Transaction>

    fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Observable<Transaction>

    fun deleteTransaction(id: Int): Observable<Response<Unit>>
}

class MainRemoteRepositoryImpl(
    private val purchase: PurchaseService,
    private val sale: SaleService,
    private val transactions: TransactionService
) : MainRemoteRepository {
    override fun getAllPurchaseTypeTransactions(): Observable<Transactions> {
        return purchase.getAllPurchaseTypeTransactions()
    }

    override fun getPurchaseTypeTransaction(id: Int): Observable<Transaction> {
        return purchase.getPurchaseById(id)
    }

    override fun getAllSaleTypeTransactions(): Observable<Transactions> {
        return sale.getAllSaleTypeTransactions()
    }

    override fun getSaleTypeTransaction(id: Int): Observable<Transaction> {
        return sale.getSaleTypeTransaction(id)
    }

    override fun getTransactions(): Observable<Transactions> {
        return transactions.getTransactions()
    }

    override fun getTransactionById(id: Int): Observable<Transaction> {
        return transactions.getTransactionById(id)
    }

    override fun postTransaction(
        name: String,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Observable<Transaction> {
        return transactions.postTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            desc = description
        )
    }

    override fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Observable<Transaction> {
        return transactions.updateTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            desc = description,
            id = id
        )
    }

    override fun deleteTransaction(id: Int): Observable<Response<Unit>> {
        return transactions.deleteTransaction(id)
    }
}