package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import io.reactivex.rxjava3.core.Observable

@Dao
interface BalanceSheetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalanceSheetToLocal(query: BalanceSheetLocal)

    @Query("DELETE FROM BalanceSheetLocal WHERE balance_sheet_id = :id")
    fun deleteBalanceSheetSpecificAccount(id: Int)

    @Query("DELETE FROM BalanceSheetLocal")
    fun deleteAllAccountsOnBalanceSheet()

    @Query("SELECT * FROM BalanceSheetLocal")
    fun getBalanceSheet(): Observable<List<BalanceSheetLocal>>

    @Query("SELECT * FROM BalanceSheetLocal WHERE account_name = :accountName")
    fun getBalanceSheet(accountName: String): Observable<BalanceSheetLocal>

    @Query(
        """
        UPDATE BalanceSheetLocal
        SET account_balance = :value
        WHERE account_name = :accountName
    """
    )
    fun updateBalanceSheetByAccountName(accountName: String, value: String)
}