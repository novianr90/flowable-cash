package id.novian.flowablecash.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionDomain(
    val id: Int,
    val transactionName: String,
    val transactionDate: String,
    val transactionType: String,
    val transactionDescription: String,
    val total: Int,
    val createdAt: String,
    val updatedAt: String,
    val payment: String,
) : Parcelable
