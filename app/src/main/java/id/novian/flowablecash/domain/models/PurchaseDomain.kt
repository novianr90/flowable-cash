package id.novian.flowablecash.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseDomain(
    val transactionName: String,
    val transactionDate: String,
    val transactionTotal: Int,
    val transactionDescription: String
) : Parcelable
