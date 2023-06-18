package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CashReceiptJournalLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cash_receipt_journal_id")
    val id: Int?,
    @ColumnInfo(name = "cash_receipt_date")
    val date: String,
    @ColumnInfo(name = "cash_receipt_description")
    val description: String,
    @ColumnInfo(name = "cash_receipt_balance")
    val balance: String
)
