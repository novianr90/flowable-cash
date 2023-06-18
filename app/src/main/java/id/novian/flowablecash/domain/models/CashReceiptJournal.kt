package id.novian.flowablecash.domain.models

import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance

data class CashReceiptJournal(
    val id: Int?,
    val date: String,
    val description: String,
    val balance: AccountBalance
)
