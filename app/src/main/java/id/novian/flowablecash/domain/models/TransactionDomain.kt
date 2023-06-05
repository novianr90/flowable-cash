package id.novian.flowablecash.domain.models

import android.os.Parcelable
import id.novian.flowablecash.data.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionDomain(
    val transactionName: String,
    val transactionDate: String,
    val transactionType: TransactionType,
    val transactionDescription: String,
    val total: Long
) : Parcelable
