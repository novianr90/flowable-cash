package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionLocal(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val transactionName: String,
    @ColumnInfo(name = "date") val transactionDate: String,
    @ColumnInfo(name = "type") val transactionType: String,
    @ColumnInfo(name = "total") val transactionTotal: Int,
    @ColumnInfo(name = "fee_type") val feeType: String,
    @ColumnInfo(name = "transaction_fee") val fee: Int,
    @ColumnInfo(name = "description") val transactionDescription: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "transaction_payment") val payment: String,
    @ColumnInfo(name = "already_posted") val alreadyPosted: Int
)
