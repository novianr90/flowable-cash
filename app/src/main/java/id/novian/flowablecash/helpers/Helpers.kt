package id.novian.flowablecash.helpers

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.FeeType
import id.novian.flowablecash.data.TransactionType
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
            "Device" -> AccountName.DEVICE
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
            AccountName.DEVICE -> "Device"
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
}