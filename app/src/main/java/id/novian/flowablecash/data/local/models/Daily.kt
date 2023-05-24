package id.novian.flowablecash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.novian.flowablecash.data.TransactionType

@Entity
data class Daily(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val referenceNumber: Int,
    val date: String,
    val transactionName: String,
    val transactionType: TransactionType,
    val total: Long,
    val description: String,
)
