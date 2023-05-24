package id.novian.flowablecash.domain.models

import id.novian.flowablecash.data.TransactionType

data class Daily(
    val transactionName: String,
    val transactionDate: String,
    val transactionType: TransactionType,
    val transactionDescription: String,
    val total: Long
)
