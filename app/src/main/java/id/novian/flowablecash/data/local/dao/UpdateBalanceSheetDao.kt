package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.local.models.UpdateModelBalanceSheet
import io.reactivex.rxjava3.core.Observable

@Dao
interface UpdateBalanceSheetDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNewQueryForUpdateBalanceSheet(query: UpdateModelBalanceSheet)

    @Query("SELECT * FROM UpdateModelBalanceSheet")
    fun getUpdateBalanceSheet(): Observable<List<UpdateModelBalanceSheet>>

    @Query("""
        SELECT * FROM UpdateModelBalanceSheet
        WHERE account_name_debit = :accountNameDebit & alreadyUpdated = 0
    """)
    fun getUpdateOneBalanceSheetDebit(accountNameDebit: String): Observable<UpdateModelBalanceSheet>

    @Query("""
        SELECT * FROM UpdateModelBalanceSheet
        WHERE account_name_credit = :accountNameCredit & alreadyUpdated = 0
    """)
    fun getUpdateOneBalanceSheetCredit(accountNameCredit: String): Observable<UpdateModelBalanceSheet>

    @Query("""
        UPDATE UpdateModelBalanceSheet
        SET alreadyUpdated = 1
        WHERE account_name_debit = :accountNameDebit & account_name_credit = :accountNameCredit
    """)
    fun setUpdateBalanceSheet(accountNameDebit: String, accountNameCredit: String)

    @Query("""
        DELETE FROM UpdateModelBalanceSheet
        WHERE account_name_debit = :accountNameDebit
    """)
    fun deleteSpecificAccountDebit(accountNameDebit: String)

    @Query("""
        DELETE FROM UpdateModelBalanceSheet
        WHERE account_name_debit = :accountNameCredit
    """)
    fun deleteSpecificAccountCredit(accountNameCredit: String)
}