package id.novian.flowablecash.helpers

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
}