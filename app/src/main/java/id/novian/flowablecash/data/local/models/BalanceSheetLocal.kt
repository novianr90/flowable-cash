package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BalanceSheetLocal(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "balance_sheet_id") val id: Int,
    @ColumnInfo(name = "account_no") val accountNo: String,
    @ColumnInfo(name = "account_name") val accountName: String,
    @ColumnInfo(name = "account_balance") val balance: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String
)
