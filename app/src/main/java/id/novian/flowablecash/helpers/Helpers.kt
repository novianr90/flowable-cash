package id.novian.flowablecash.helpers

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.domain.models.TransactionDomain
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

object Helpers {

    fun numberFormatter(number: Int?): String {
        return if (number != null && number != 0) {
            val formatter = DecimalFormat("#,###,###")
            formatter.format(number)
        } else {
            ""
        }
    }

    fun formatCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.currency = Currency.getInstance("IDR")
        return format.format(amount.toLong())
    }

    fun getMonthName(month: Int): String {
        val locale = Locale.getDefault()
        val dateFormat = DateFormatSymbols(locale)
        val months = dateFormat.months

        return if (month in 1..12) {
            months[month - 1]
        } else {
            "Invalid Month"
        }
    }

    fun stringToAccountName(value: String): AccountName {
        return when (value) {
            "Kas" -> AccountName.KAS
            "Persediaan Barang Dagang" -> AccountName.PERSEDIAANBARANGDAGANG
            "Perlengkapan" -> AccountName.PERLENGKAPAN
            "Hutang Dagang" -> AccountName.HUTANGDAGANG
            "Piutang Dagang" -> AccountName.PIUTANG
            "Modal" -> AccountName.MODALOWNER
            "Laba Disimpan" -> AccountName.LABADISIMPAN
            "Mengambil Laba" -> AccountName.PRIVE
            "Penjualan" -> AccountName.PENJUALAN
            "Pembelian" -> AccountName.PEMBELIAN
            "Beban Ongkos" -> AccountName.BEBANONGKOS
            "Beban Pengemasan" -> AccountName.BEBANPENGEMASAN
            "Beban Penyusutan" -> AccountName.BEBANPENYUSUTAN
            "Akumulasi Penyusutan Perlengkapan" -> AccountName.AKUMULASIPENYUSUTANPERLENGKAPAN
            "Beban Lainnya" -> AccountName.BEBANLAINNYA
            "Beban Operasional" -> AccountName.BEBANOPS
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
            AccountName.PIUTANG -> "Piutang Dagang"
            AccountName.MODALOWNER -> "Modal"
            AccountName.LABADISIMPAN -> "Laba Disimpan"
            AccountName.PRIVE -> "Mengambil Laba"
            AccountName.PENJUALAN -> "Penjualan"
            AccountName.PEMBELIAN -> "Pembelian"
            AccountName.BEBANONGKOS -> "Beban Ongkos"
            AccountName.BEBANPENGEMASAN -> "Beban Pengemasan"
            AccountName.BEBANPENYUSUTAN -> "Beban Penyusutan"
            AccountName.BEBANLAINNYA -> "Beban Lainnya"
            AccountName.BEBANOPS -> "Beban Operasional"
            else -> "Unknown"
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