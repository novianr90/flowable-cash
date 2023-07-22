package id.novian.flowablecash.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.data.remote.models.input.AccountInfo
import id.novian.flowablecash.data.remote.models.input.InputCreateAccounts
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
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
    fun getTransactions(): Maybe<Transactions>
    fun getTransactionById(id: Int): Observable<Transaction>

    fun postTransaction(
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String,
        payment: String
    ): Observable<Unit>

    fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String,
        alreadyPosted: Int
    ): Completable

    fun deleteTransaction(id: Int): Observable<Response<Unit>>

    fun getAllTransactionByType(type: String): Maybe<Transactions>

    // Balance Sheet
    fun getAllAccounts(month: Int): Maybe<BalanceSheets>
    fun getAccount(accountName: String, month: Int): Maybe<BalanceSheet>
    fun updateAccount(
        id: Int?,
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable
    fun updateSpecialAccount(
        id: Int? = null,
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable
    fun recordNewAccounts(
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable
}

class MainRemoteRepositoryImpl(
    private val purchase: PurchaseService,
    private val sale: SaleService,
    private val transactions: TransactionService,
    private val balanceSheet: BalanceSheetService,
    private val gson: Gson
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

    override fun getTransactions(): Maybe<Transactions> {
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
        description: String,
        payment: String
    ): Observable<Unit> {
        return transactions.postTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            feeType = feeType,
            fee = fee,
            desc = description,
            payment = payment
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
        description: String,
        alreadyPosted: Int
    ): Completable {
        return transactions.updateTransaction(
            name = name,
            date = date,
            type = type,
            total = total,
            desc = description,
            id = id,
            feeType = feeType,
            fee = fee,
            alreadyPosted = alreadyPosted
        )
    }

    override fun deleteTransaction(id: Int): Observable<Response<Unit>> {
        return transactions.deleteTransaction(id)
    }

    override fun getAllTransactionByType(type: String): Maybe<Transactions> {
        return transactions.getAllTransactionByType(type)
    }

    override fun getAllAccounts(month: Int): Maybe<BalanceSheets> {
        return balanceSheet.getAllAccounts(month)
    }

    override fun getAccount(accountName: String, month: Int): Maybe<BalanceSheet> {
        return balanceSheet.getAllAccountsByAccountName(account_name = accountName, month = month)
    }

    override fun updateAccount(
        id: Int?,
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable {
        val newQuery = AccountInfo(balanceSheetId = id, accountName = accountName, accountBalance = balance, accountMonth = month)
        return balanceSheet.updateAccounts(newQuery)
    }

    override fun updateSpecialAccount(
        id: Int?,
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable {
        val newQuery = AccountInfo(accountName = accountName, accountBalance = balance, accountMonth = month, balanceSheetId = id)
        return balanceSheet.updateSpecialAccounts(newQuery)
    }

    override fun recordNewAccounts(
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable {
        val newQuery = InputCreateAccounts(accountName = accountName, balance = balance, month = month)
        return balanceSheet.recordNewAccounts(newQuery)
            .doOnComplete {
                Log.d("MainRemote", "Invoked! $newQuery")
            }
            .doOnError {
                it.printStackTrace()
                Log.d("MainRemote", "Error! $newQuery")
            }
    }
}