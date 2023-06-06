package id.novian.flowablecash.domain.mapper


import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers.transactionTypeChanger
import id.novian.flowablecash.helpers.Helpers.transactionTypeDecider
import id.novian.flowablecash.helpers.Mapper

class TransactionMapper : Mapper<Transaction, TransactionDomain> {
    override fun mapToDomain(model: Transaction): TransactionDomain {
        return TransactionDomain(
            id = model.id,
            transactionName = model.name,
            transactionDate = model.date,
            transactionType = transactionTypeDecider(model.type),
            total = model.total,
            transactionDescription = model.description,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapToModel(domain: TransactionDomain): Transaction {
        return Transaction(
            id = domain.id,
            name = domain.transactionName,
            date = domain.transactionDate,
            type = transactionTypeChanger(domain.transactionType),
            total = domain.total,
            description = domain.transactionDescription,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

class LocalMapper : Mapper<TransactionLocal, TransactionDomain> {
    override fun mapToDomain(model: TransactionLocal): TransactionDomain {
        return TransactionDomain(
            id = model.id,
            transactionName = model.transactionName,
            transactionDate = model.transactionDate,
            transactionType = transactionTypeDecider(model.transactionType),
            total = model.transactionTotal,
            transactionDescription = model.transactionDescription,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapToModel(domain: TransactionDomain): TransactionLocal {
        return TransactionLocal(
            id = domain.id,
            transactionName = domain.transactionName,
            transactionDate = domain.transactionDate,
            transactionType = transactionTypeChanger(domain.transactionType),
            transactionTotal = domain.total,
            transactionDescription = domain.transactionDescription,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }

}