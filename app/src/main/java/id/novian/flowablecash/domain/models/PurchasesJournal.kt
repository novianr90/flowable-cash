package id.novian.flowablecash.domain.models

data class PurchasesJournal(
    val id: Int,
    val date: String,
    val description: String,
    val debit: Int,
    val credit: Int
)
