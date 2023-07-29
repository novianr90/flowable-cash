package id.novian.flowablecash.domain.models

data class CashReceiptJournal(
    val id: Int,
    val name: String,
    val date: String,
    val description: String,
    val debit: Int,
    val credit: Int,
)
