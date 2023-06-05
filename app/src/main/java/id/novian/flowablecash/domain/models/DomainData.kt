package id.novian.flowablecash.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainData(
    val listOfTransactions: List<TransactionDomain>? = null,
    val listOfSales: List<SaleDomain>? = null,
    val listOfPurchases: List<PurchaseDomain>? = null
) : Parcelable
