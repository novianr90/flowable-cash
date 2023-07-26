package id.novian.flowablecash.domain.models

import android.os.Parcelable
import id.novian.flowablecash.data.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionDomain(
    val id: Int,
    val transactionName: String,
    val transactionDate: String,
    val transactionType: TransactionType,
    val transactionDescription: String,
    val total: Int,
    val fee: Int,
    val feeType: String,
    val createdAt: String,
    val updatedAt: String,
    val payment: String,
    val alreadyPosted: Int
) : Parcelable
