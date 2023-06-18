package id.novian.flowablecash.helpers

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.FeeType
import id.novian.flowablecash.data.TransactionType
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import java.text.DecimalFormat

object Helpers {
    fun numberFormatter(number: Int?): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(number)
    }

    fun transactionTypeDecider(value: String): TransactionType {
        return when (value) {
            "Sale" -> TransactionType.SALE
            "Purchase" -> TransactionType.PURCHASE
            else -> TransactionType.UNKNOWN
        }
    }

    fun transactionTypeChanger(transactionType: TransactionType): String {
        return when (transactionType) {
            TransactionType.PURCHASE -> "Purchase"
            TransactionType.SALE -> "Sale"
            else -> ""
        }
    }

    fun feeTypeDecider(value: String): FeeType {
        return when (value) {
            "Purchase" -> FeeType.PURCHASE
            "Sale" -> FeeType.SALE
            else -> FeeType.UNKNOWN
        }
    }

    fun feeTypeChanger(value: FeeType): String {
        return when (value) {
            FeeType.SALE -> "Sale"
            FeeType.PURCHASE -> "Purchase"
            else -> ""
        }
    }

    fun stringToAccountName(value: String): AccountName {
        return when (value) {
            "Kas" -> AccountName.KAS
            "Persediaan Barang Dagang" -> AccountName.PERSEDIAANBARANGDAGANG
            "Perlengkapan" -> AccountName.PERLENGKAPAN
            "Hutang Dagang" -> AccountName.HUTANGDAGANG
            "Modal Owner" -> AccountName.MODALOWNER
            "Laba Disimpan" -> AccountName.LABADISIMPAN
            "Mengambil Laba" -> AccountName.PRIVE
            "Penjualan" -> AccountName.PENJUALAN
            "Pembelian" -> AccountName.PEMBELIAN
            "Beban Penjualan" -> AccountName.BEBANPENJUALAN
            "Beban Pembelian" -> AccountName.BEBANPEMBELIAN
            else -> AccountName.UNKNOWN
        }
    }

    fun accountNameToString(accountName: AccountName): String {
        return when (accountName) {
            AccountName.KAS -> "Kas"
            AccountName.PERSEDIAANBARANGDAGANG -> "Persediaan Barang Dagang"
            AccountName.PERLENGKAPAN -> "Perlengkapan"
            AccountName.HUTANGDAGANG -> "Hutang Dagang"
            AccountName.MODALOWNER -> "Modal Owner"
            AccountName.LABADISIMPAN -> "Laba Disimpan"
            AccountName.PRIVE -> "Mengambil Laba"
            AccountName.PENJUALAN -> "Penjualan"
            AccountName.PEMBELIAN -> "Pembelian"
            AccountName.BEBANPENJUALAN -> "Beban Penjualan"
            AccountName.BEBANPEMBELIAN -> "Beban Pembelian"
            else -> "Unknown"
        }
    }

    fun debitCreditDeciderForBalanceSheet(accountName: AccountName, value: Int): AccountBalance {
        return when (accountName) {
            AccountName.KAS -> AccountBalance(debit = value, credit = 0)
            AccountName.PERSEDIAANBARANGDAGANG -> AccountBalance(debit = value, credit = 0)
            AccountName.PERLENGKAPAN -> AccountBalance(debit = value, credit = 0)
            AccountName.AKUMULASIPENYUSUTANPERLENGKAPAN -> AccountBalance(debit = 0, credit = value)
            AccountName.HUTANGDAGANG -> AccountBalance(debit = 0, credit = value)
            AccountName.MODALOWNER -> AccountBalance(debit = 0, credit = value)
            AccountName.LABADISIMPAN -> AccountBalance(debit = 0, credit = value)
            AccountName.PRIVE -> AccountBalance(debit = 0, credit = value)
            AccountName.BEBANPENJUALAN -> AccountBalance(debit = value, credit = 0)
            AccountName.PEMBELIAN -> AccountBalance(debit = value, credit = 0)
            else -> AccountBalance(0, 0)
        }
    }
}