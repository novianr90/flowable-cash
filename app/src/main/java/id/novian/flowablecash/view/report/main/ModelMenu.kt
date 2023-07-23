package id.novian.flowablecash.view.report.main

import id.novian.flowablecash.R

data class ModelMenu(
    val title: String,
    val image: Int
)

val menuReportItems = listOf(
    ModelMenu("Jurnal Penjualan", R.drawable.ic_cash_receipt),
    ModelMenu("Jurnal Pembelian", R.drawable.ic_purchases_journal),
    ModelMenu("Neraca Saldo", R.drawable.ic_balance_sheet),
    ModelMenu("Laporan Laba Rugi", R.drawable.ic_income_statements),
    ModelMenu("Laporan Posisi Keuangan", R.drawable.ic_financial_position)
)