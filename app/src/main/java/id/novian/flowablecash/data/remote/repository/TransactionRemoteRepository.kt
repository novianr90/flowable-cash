package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.data.remote.service.TransactionService
import io.reactivex.rxjava3.core.Observable

interface TransactionRemoteRepository {
    fun getTransactions(): Observable<Transactions>

    fun getTransactionById(id: Int): Observable<Transaction>

    fun postTransaction(
        name: String,
        date: String,
        type: String,
        total: Int,
        description: String
    ): Observable<Transaction>

    fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        type: String,
        total: Int,
        description: String
    ): Observable<Transaction>

    fun deleteTransaction(id: Int): Observable<String>
}

class TransactionRemoteRepositoryImpl(
    private val api: TransactionService
) : TransactionRemoteRepository {
    override fun getTransactions(): Observable<Transactions> {
        return api.getTransactions()
    }

    override fun getTransactionById(id: Int): Observable<Transaction> {
        return api.getTransactionById(id)
    }

    override fun postTransaction(
        name: String,
        date: String,
        type: String,
        total: Int,
        description: String
    ): Observable<Transaction> {
        return api.postTransaction(
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
        type: String,
        total: Int,
        description: String
    ): Observable<Transaction> {
        return api.updateTransaction(
            id = id,
            name = name,
            date = date,
            type = type,
            total = total,
            desc = description
        )
    }

    override fun deleteTransaction(id: Int): Observable<String> {
        return api.deleteTransaction(id)
    }
}