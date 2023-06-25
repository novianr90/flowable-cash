package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CashReceiptReport(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "cash_receipt_id") val id: Int,
    @ColumnInfo(name = "cash_receipt_date") val date: String,
    @ColumnInfo(name = "cash_receipt_description") val description: String,
    @ColumnInfo(name = "cash_receipt_debit") val cashReceiptDebit: Int,
    @ColumnInfo(name = "cash_receipt_credit") val cashReceiptCredit: Int,
    val accountAlreadyInserted: Int
)
