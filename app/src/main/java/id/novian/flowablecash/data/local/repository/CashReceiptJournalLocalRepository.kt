package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.CashReceiptJournalDao
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import io.reactivex.rxjava3.core.Observable

interface CashReceiptJournalLocalRepository {
    fun insertNewData(query: CashReceiptJournalLocal)
    fun getJournal(): Observable<List<CashReceiptJournalLocal>>
    fun updateBalanceByIdOrDate(newBalance: String, id: Int?, date: String?)
    fun deleteOneAccount(id: Int)
    fun deleteJournal()
}

class CashReceiptJournalLocalRepositoryImpl(
    private val dao: CashReceiptJournalDao
) : CashReceiptJournalLocalRepository {
    override fun insertNewData(query: CashReceiptJournalLocal) {
        val newQuery = CashReceiptJournalLocal(
            id = if (query.id == 0) null else null,
            date = query.date,
            description = query.description,
            balance = query.balance
        )
        return dao.insertNewAccountForCashReceiptJournal(newQuery)
    }

    override fun getJournal(): Observable<List<CashReceiptJournalLocal>> {
        return dao.getCashReceiptJournal()
    }

    override fun updateBalanceByIdOrDate(newBalance: String, id: Int?, date: String?) {
        when {
            id != null -> dao.update(newBalance, id = id)
            date != null -> dao.update(newBalance, date = date)
        }
    }

    override fun deleteOneAccount(id: Int) {
        dao.deleteOneCashReceiptJournal(id)
    }

    override fun deleteJournal() {
        dao.deleteCurrentReceiptJournal()
    }
}
