package id.novian.flowablecash.domain.models

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.Balance

data class BalanceSheetDomain(
    val id: Int,
    val accountNo: String,
    val accountName: AccountName,
    val balance: Balance,
    val createdAt: String,
    val updatedAt: String,
)
