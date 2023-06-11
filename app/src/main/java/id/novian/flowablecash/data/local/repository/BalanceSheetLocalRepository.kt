package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.BalanceSheetDao
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import io.reactivex.rxjava3.core.Observable

interface BalanceSheetLocalRepository {
    fun insertBalanceSheetToLocal(query: BalanceSheetLocal): Observable<Unit>

    fun deleteBalanceSheetSpecificAccount(id: Int): Observable<Unit>

    fun deleteAllAccountsOnBalanceSheet(): Observable<Unit>

    fun getBalanceSheet(): Observable<BalanceSheetLocal>
}

class BalanceSheetLocalRepositoryImpl(
    private val dao: BalanceSheetDao
) : BalanceSheetLocalRepository {
    override fun insertBalanceSheetToLocal(query: BalanceSheetLocal): Observable<Unit> {
        return dao.insertBalanceSheetToLocal(query)
    }

    override fun deleteBalanceSheetSpecificAccount(id: Int): Observable<Unit> {
        return dao.deleteBalanceSheetSpecificAccount(id)
    }

    override fun deleteAllAccountsOnBalanceSheet(): Observable<Unit> {
        return dao.deleteAllAccountsOnBalanceSheet()
    }

    override fun getBalanceSheet(): Observable<BalanceSheetLocal> {
        return dao.getBalanceSheet()
    }

}