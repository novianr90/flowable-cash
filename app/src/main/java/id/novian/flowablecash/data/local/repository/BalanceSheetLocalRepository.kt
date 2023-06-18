package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.BalanceSheetDao
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import io.reactivex.rxjava3.core.Observable

interface BalanceSheetLocalRepository {
    fun insertBalanceSheetToLocal(query: BalanceSheetLocal)

    fun deleteBalanceSheetSpecificAccount(id: Int)

    fun deleteAllAccountsOnBalanceSheet()

    fun getBalanceSheet(): Observable<List<BalanceSheetLocal>>

    fun getBalanceSheet(accountName: String): Observable<BalanceSheetLocal>

    fun updateBalanceSheetByAccountName(accountName: String, value: String)
}

class BalanceSheetLocalRepositoryImpl(
    private val dao: BalanceSheetDao
) : BalanceSheetLocalRepository {
    override fun insertBalanceSheetToLocal(query: BalanceSheetLocal) {
        return dao.insertBalanceSheetToLocal(query)
    }

    override fun deleteBalanceSheetSpecificAccount(id: Int) {
        return dao.deleteBalanceSheetSpecificAccount(id)
    }

    override fun deleteAllAccountsOnBalanceSheet() {
        return dao.deleteAllAccountsOnBalanceSheet()
    }

    override fun getBalanceSheet(): Observable<List<BalanceSheetLocal>> {
        return dao.getBalanceSheet()
    }

    override fun getBalanceSheet(accountName: String): Observable<BalanceSheetLocal> {
        return dao.getBalanceSheet(accountName)
    }

    override fun updateBalanceSheetByAccountName(accountName: String, value: String) {
        return dao.updateBalanceSheetByAccountName(accountName, value)
    }

}