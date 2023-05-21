package id.novian.flowablecash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.novian.flowablecash.data.Balance
import id.novian.flowablecash.data.TransactionType
import java.sql.Date

@Entity
data class Daily(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val referenceNumber: Int,
    val date: Date,
    val transactionName: String,
    val transactionType: TransactionType,
    val total: Balance,
    val description: String,
)
