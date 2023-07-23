package id.novian.flowablecash.view.report.financial_position

import id.novian.flowablecash.domain.models.AccountDomain

data class FinancialPositionParentData(
    val title: String,
    val data: List<AccountDomain>,
    val total: Int
)
