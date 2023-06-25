package id.novian.flowablecash.helpers

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.FeeType
import id.novian.flowablecash.data.TransactionType
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.domain.models.TransactionDomain
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            "Modal" -> AccountName.MODALOWNER
            "Laba Disimpan" -> AccountName.LABADISIMPAN
            "Mengambil Laba" -> AccountName.PRIVE
            "Penjualan" -> AccountName.PENJUALAN
            "Pembelian" -> AccountName.PEMBELIAN
            "Beban Penjualan" -> AccountName.BEBANPENJUALAN
            "Beban Pembelian" -> AccountName.BEBANPEMBELIAN
            "Akumulasi Penyusutan Perlengkapan" -> AccountName.AKUMULASIPENYUSUTANPERLENGKAPAN
            else -> AccountName.UNKNOWN
        }
    }

    fun accountNameToString(accountName: AccountName): String {
        return when (accountName) {
            AccountName.KAS -> "Kas"
            AccountName.PERSEDIAANBARANGDAGANG -> "Persediaan Barang Dagang"
            AccountName.AKUMULASIPENYUSUTANPERLENGKAPAN -> "Akumulasi Penyusutan Perlengkapan"
            AccountName.PERLENGKAPAN -> "Perlengkapan"
            AccountName.HUTANGDAGANG -> "Hutang Dagang"
            AccountName.MODALOWNER -> "Modal"
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

    fun dateFormatFromNonStringToString(inputDate: String?): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val date = inputDate?.let {
            try {
                inputFormat.parse(inputDate)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        return outputFormat.format(date ?: Date(0))
    }

    private fun Date.isInRange(startDate: Date, endDate: Date): Boolean {
        return this in startDate..endDate
    }

    fun filterTransactionsByDateRange(
        models: List<TransactionDomain>,
        rangeStartToEndDate: String
    ): List<TransactionDomain> {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val rangeDates = rangeStartToEndDate.split(" - ")

        val startDate = rangeDates[0]
        val endDate = rangeDates[1]

        val parsedStartDate = dateFormat.parse(startDate)
        val parsedEndDate = dateFormat.parse(endDate)

        return models.filter {
            val date = dateFormat.parse(it.transactionDate)
            date != null && date.isInRange(parsedStartDate, parsedEndDate)
        }
    }
}