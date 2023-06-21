package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<Unit>

    fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<Transaction>

    fun deleteTransaction(id: Int): Observable<Response<Unit>>

    // Balance Sheet
    fun getBalanceSheet(): Observable<BalanceSheets>
    fun getBalanceSheet(accountName: String): Observable<BalanceSheet>
    fun updateBalanceSheet(
        newAccountName: String,
        newBalance: String
    ): Observable<BalanceSheet>
}

class MainRemoteRepositoryImpl(
    private val purchase: PurchaseService,
    private val sale: SaleService,
    private val transactions: TransactionService,
    private val balanceSheet: BalanceSheetService
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
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<Unit> {
        return transactions.postTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            feeType = feeType,
            fee = fee,
            desc = description
        )
    }

    override fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<Transaction> {
        return transactions.updateTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            desc = description,
            id = id,
            feeType = feeType,
            fee = fee,
        )
    }

    override fun deleteTransaction(id: Int): Observable<Response<Unit>> {
        return transactions.deleteTransaction(id)
    }

    override fun getBalanceSheet(): Observable<BalanceSheets> {
        return balanceSheet.getBalanceSheets()
    }

    override fun getBalanceSheet(accountName: String): Observable<BalanceSheet> {
        return balanceSheet.getBalanceSheetByAccountName(accountName)
    }

    override fun updateBalanceSheet(
        newAccountName: String,
        newBalance: String
    ): Observable<BalanceSheet> {
        val accountName = newAccountName.toRequestBody(MultipartBody.FORM)
        val balance = newBalance.toRequestBody(MultipartBody.FORM)

        return balanceSheet.updateBalanceSheet(accountName, balance)
    }
}