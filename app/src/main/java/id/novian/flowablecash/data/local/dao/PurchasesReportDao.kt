package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.PurchasesReport
import io.reactivex.rxjava3.core.Observable

@Dao
interface PurchasesReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewAccountToPurchases(query: PurchasesReport)

    @Query("SELECT * FROM PurchasesReport")
    fun getPurchasesReport(): Observable<List<PurchasesReport>>

    @Query("DELETE FROM PurchasesReport")
    fun deletePurchasesReport()

    @Query("DELETE FROM PurchasesReport WHERE purchases_report_id = :id")
    fun deleteSpecificPurchasesAccount(id: Int)
}