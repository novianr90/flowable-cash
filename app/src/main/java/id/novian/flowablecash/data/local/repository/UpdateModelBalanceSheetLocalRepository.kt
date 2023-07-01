package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.UpdateBalanceSheetDao
import id.novian.flowablecash.data.local.models.UpdateModelBalanceSheet
import io.reactivex.rxjava3.core.Observable

interface UpdateModelBalanceSheetLocalRepository {
    fun insertNewQueryForUpdateBalanceSheet(query: UpdateModelBalanceSheet)
    fun getUpdateBalanceSheet(): Observable<List<UpdateModelBalanceSheet>>
    fun getDebitAccountNameForUpdate(accountNameDebit: String): Observable<UpdateModelBalanceSheet>
    fun getCreditAccountNameForUpdate(accountNameCredit: String): Observable<UpdateModelBalanceSheet>
    fun setUpdateBalanceSheet(accountNameDebit: String, accountNameCredit: String): Observable<Unit>
    fun deleteSpecificAccountDebit(accountName: String): Observable<Unit>
    fun deleteSpecificAccountCredit(accountName: String): Observable<Unit>
}

class UpdateModelBalanceSheetLocalRepositoryImpl(
    private val dao: UpdateBalanceSheetDao
): UpdateModelBalanceSheetLocalRepository {
    override fun insertNewQueryForUpdateBalanceSheet(query: UpdateModelBalanceSheet) {
        val newQuery = UpdateModelBalanceSheet(
            id = query.id,
            accountNameDebit = query.accountNameDebit,
            accountNameCredit = query.accountNameCredit,
            debit = query.debit,
            credit = query.credit,
            alreadyUpdated = 0
        )
        return dao.insertNewQueryForUpdateBalanceSheet(newQuery)
    }

    override fun getUpdateBalanceSheet(): Observable<List<UpdateModelBalanceSheet>> {
        return dao.getUpdateBalanceSheet()
    }

    override fun getDebitAccountNameForUpdate(accountNameDebit: String): Observable<UpdateModelBalanceSheet> {
        return dao.getUpdateOneBalanceSheetDebit(accountNameDebit)
    }

    override fun getCreditAccountNameForUpdate(accountNameCredit: String): Observable<UpdateModelBalanceSheet> {
        return dao.getUpdateOneBalanceSheetCredit(accountNameCredit)
    }

    override fun setUpdateBalanceSheet(
        accountNameDebit: String,
        accountNameCredit: String
    ): Observable<Unit> {
        return Observable.just(dao.setUpdateBalanceSheet(accountNameDebit = accountNameDebit, accountNameCredit = accountNameCredit))
    }

    override fun deleteSpecificAccountDebit(accountName: String): Observable<Unit> {
        return Observable.just(dao.deleteSpecificAccountDebit(accountName))
    }

    override fun deleteSpecificAccountCredit(accountName: String): Observable<Unit> {
        return Observable.just(dao.deleteSpecificAccountCredit(accountName))
    }
}