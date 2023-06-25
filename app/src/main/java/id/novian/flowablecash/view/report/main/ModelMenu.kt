package id.novian.flowablecash.view.report.main

import id.novian.flowablecash.R

data class ModelMenu(
    val title: String,
    val image: Int
)

val menuReportItems = listOf(
    ModelMenu("Cash Receipt Journal", R.drawable.ic_cash_receipt),
    ModelMenu("Purchases Journal", R.drawable.ic_purchases_journal),
    ModelMenu("Balance Sheet", R.drawable.ic_balance_sheet),
)