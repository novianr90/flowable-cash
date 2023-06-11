package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import io.reactivex.rxjava3.core.Observable

@Dao
interface BalanceSheetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBalanceSheetToLocal(query: BalanceSheetLocal): Observable<Unit>

    @Query("DELETE FROM BalanceSheetLocal WHERE balance_sheet_id = :id")
    fun deleteBalanceSheetSpecificAccount(id: Int): Observable<Unit>

    @Query("DELETE FROM BalanceSheetLocal")
    fun deleteAllAccountsOnBalanceSheet(): Observable<Unit>

    @Query("SELECT * FROM BalanceSheetLocal")
    fun getBalanceSheet(): Observable<BalanceSheetLocal>
}