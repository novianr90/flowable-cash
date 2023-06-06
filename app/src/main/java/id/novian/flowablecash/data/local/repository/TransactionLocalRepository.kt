package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.TransactionDao
import id.novian.flowablecash.data.local.models.TransactionLocal
import io.reactivex.rxjava3.core.Observable

interface TransactionLocalRepository {
    fun insertTransaction(query: TransactionLocal)
    fun updateTransaction(query: TransactionLocal)
    fun deleteTransaction(query: Int)
    fun getAllTransaction(): Observable<List<TransactionLocal>>
    fun getAllTypeTransaction(queryType: String): Observable<List<TransactionLocal>>
    fun getTransactionById(id: Int): Observable<TransactionLocal>
}

class TransactionLocalRepositoryImpl(
    private val dao: TransactionDao
) : TransactionLocalRepository {
    override fun insertTransaction(query: TransactionLocal) {
        return dao.insertTransactions(query)
    }

    override fun updateTransaction(query: TransactionLocal) {
        return dao.updateTransaction(query)
    }

    override fun deleteTransaction(query: Int) {
        return dao.deleteTransaction(query)
    }

    override fun getAllTransaction(): Observable<List<TransactionLocal>> {
        return dao.getAllTransactions()
    }

    override fun getAllTypeTransaction(queryType: String): Observable<List<TransactionLocal>> {
        return dao.getAllTypeTransactions(queryType)
    }

    override fun getTransactionById(id: Int): Observable<TransactionLocal> {
        return dao.getTransactionById(id)
    }

}