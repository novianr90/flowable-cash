package id.novian.flowablecash.domain.mapper

import id.novian.flowablecash.data.TransactionType
import id.novian.flowablecash.data.remote.models.purchase.Purchase
import id.novian.flowablecash.data.remote.models.sale.Sale
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.models.PurchaseDomain
import id.novian.flowablecash.domain.models.SaleDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Mapper

class TransactionMapper : Mapper<Transaction, TransactionDomain> {
    override fun mapToDomain(model: Transaction): TransactionDomain {
        return TransactionDomain(
            transactionName = model.name,
            transactionDate = model.date,
            transactionType = mapTransactionType(model.type),
            transactionDescription = model.description,
            total = model.total.toLong()
        )
    }

    private fun mapTransactionType(type: String): TransactionType {
        return when (type) {
            "Purchase" -> TransactionType.PURCHASE
            "Sale" -> TransactionType.SALE
            else -> TransactionType.UNKNOWN
        }
    }
}

class SaleMapper : Mapper<Sale, SaleDomain> {
    override fun mapToDomain(model: Sale): SaleDomain {
        return SaleDomain(
            transactionName = model.name,
            transactionDate = model.date,
            transactionDescription = model.description,
            transactionTotal = model.total
        )
    }
}

class PurchaseMapper : Mapper<Purchase, PurchaseDomain> {
    override fun mapToDomain(model: Purchase): PurchaseDomain {
        return PurchaseDomain(
            transactionName = model.name,
            transactionDate = model.date,
            transactionDescription = model.description,
            transactionTotal = model.total
        )
    }
}