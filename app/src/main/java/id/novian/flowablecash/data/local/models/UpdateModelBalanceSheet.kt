package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpdateModelBalanceSheet(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "update_balance_sheet") val id: Int?,
    @ColumnInfo(name = "account_name_debit") val accountNameDebit: String,
    @ColumnInfo(name = "account_name_credit") val accountNameCredit: String,
    val debit: Int,
    val credit: Int,
    val alreadyUpdated: Int
)
