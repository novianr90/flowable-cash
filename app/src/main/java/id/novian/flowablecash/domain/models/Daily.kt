package id.novian.flowablecash.domain.models

import id.novian.flowablecash.data.Balance
import id.novian.flowablecash.data.TransactionType
import java.util.Date

data class Daily(
    val transactionName: String,
    val transactionDate: Date,
    val transactionType: TransactionType,
    val transactionDescription: String,
    val total: Balance
)
