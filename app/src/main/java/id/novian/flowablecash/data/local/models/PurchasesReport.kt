package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PurchasesReport(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "purchases_report_id") val id: Int,
    @ColumnInfo(name = "purchases_date") val date: String,
    @ColumnInfo(name = "purchases_description") val description: String,
    @ColumnInfo(name = "purchases_debit") val purchasesDebit: Int,
    @ColumnInfo(name = "purchases_credit") val purchasesCredit: Int,
    val accountAlreadyInserted: Int
)
