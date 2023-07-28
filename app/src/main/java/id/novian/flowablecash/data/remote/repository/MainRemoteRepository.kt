package id.novian.flowablecash.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import id.novian.flowablecash.data.remote.models.Pemasukkan
import id.novian.flowablecash.data.remote.models.PemasukkanList
import id.novian.flowablecash.data.remote.models.Pengeluaran
import id.novian.flowablecash.data.remote.models.PengeluaranList
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.data.remote.models.input.AccountInfo
import id.novian.flowablecash.data.remote.models.input.InputCreateAccounts
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PemasukkanService
import id.novian.flowablecash.data.remote.service.PengeluaranService
import id.novian.flowablecash.data.remote.service.TransactionService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

interface MainRemoteRepository {
    // Pengeluaran
    fun getAllPengeluaran(): Observable<PengeluaranList>
    fun getPengeluaranById(id: Int): Observable<Pengeluaran>
    fun createPengeluaran(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?
    ): Completable

    // Pemasukkan
    fun getAllPemasukkan(): Observable<PemasukkanList>
    fun getPemasukkanById(id: Int): Observable<Pemasukkan>
    fun createPemasukkan(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?
    ): Completable

    // Transaction
    fun getAllTransactions(): Observable<Transactions>

    fun updateTransaction(
        id: Int,
        date: String,
        total: Int,
        type: String,
        description: String?
    ): Completable

    fun deleteTransaction(id: Int, type: String): Completable

    // Balance Sheet
    fun getAllAccounts(month: Int): Maybe<BalanceSheets>
    fun getAccount(accountName: String, month: Int): Maybe<AccountBalanceSheet>
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

    fun getSpecificAccounts(accountName: String): Observable<BalanceSheets>
}

class MainRemoteRepositoryImpl(
    private val pengeluaran: PengeluaranService,
    private val pemasukkan: PemasukkanService,
    private val transactions: TransactionService,
    private val balanceSheet: BalanceSheetService,
    private val gson: Gson
) : MainRemoteRepository {
    override fun getAllPengeluaran(): Observable<PengeluaranList> {
        return pengeluaran.getAllPengeluaran()
    }

    override fun getPengeluaranById(id: Int): Observable<Pengeluaran> {
        return pengeluaran.getPengeluaranById(id)
    }

    override fun createPengeluaran(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?
    ): Completable {
        return pengeluaran.postNewPemasukkanTransactions(
            name = name,
            date = date,
            total = total,
            payment = payment,
            desc = description
        )
    }

    override fun getAllPemasukkan(): Observable<PemasukkanList> {
        return pemasukkan.getAllPemasukkan()
    }

    override fun getPemasukkanById(id: Int): Observable<Pemasukkan> {
        return pemasukkan.getPemasukkanById(id)
    }

    override fun createPemasukkan(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?
    ): Completable {
        return pemasukkan.postNewPemasukkanTransactions(
            name = name,
            date = date,
            total = total,
            payment = payment,
            desc = description
        )
    }

    override fun getAllTransactions(): Observable<Transactions> {
        return transactions.getAllTransactions()
    }

    override fun updateTransaction(
        id: Int,
        date: String,
        total: Int,
        type: String,
        description: String?
    ): Completable {
        return transactions.updateTransactions(
            type = type,
            id = id,
            total = total,
            date = date,
            desc = description
        )
    }

    override fun deleteTransaction(id: Int, type: String): Completable {
        return transactions.deleteTransaction(id, type)
    }


    override fun getAllAccounts(month: Int): Maybe<BalanceSheets> {
        return balanceSheet.getAllAccounts(month)
    }

    override fun getAccount(accountName: String, month: Int): Maybe<AccountBalanceSheet> {
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
        val newQuery =
            InputCreateAccounts(accountName = accountName, balance = balance, month = month)
        return balanceSheet.recordNewAccounts(newQuery)
            .doOnComplete {
                Log.d("MainRemote", "Invoked! $newQuery")
            }
            .doOnError {
                it.printStackTrace()
                Log.d("MainRemote", "Error! $newQuery")
            }
    }

    override fun getSpecificAccounts(accountName: String): Observable<BalanceSheets> {
        return balanceSheet.getAllAccountsSpecific(accountName)
    }
}