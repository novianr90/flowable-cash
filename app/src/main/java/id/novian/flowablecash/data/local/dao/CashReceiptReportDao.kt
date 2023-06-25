package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.CashReceiptReport
import io.reactivex.rxjava3.core.Observable

@Dao
interface CashReceiptReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCashReceiptReport(query: CashReceiptReport)

    @Query("DELETE FROM CashReceiptReport")
    fun deleteCashReceiptReport()

    @Query("DELETE FROM CashReceiptReport WHERE cash_receipt_id = :id")
    fun deleteSpecificCashReceiptReport(id: Int)

    @Query("SELECT * FROM CashReceiptReport")
    fun getCashReceiptReport(): Observable<List<CashReceiptReport>>
}