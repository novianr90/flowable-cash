package id.novian.flowablecash.domain.models

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance

data class BalanceSheetDomain(
    val id: Int,
    val accountNo: String,
    val accountName: AccountName,
    val balance: AccountBalance,
    val createdAt: String,
    val updatedAt: String,
)
