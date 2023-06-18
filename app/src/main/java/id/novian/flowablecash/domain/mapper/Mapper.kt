package id.novian.flowablecash.domain.mapper


import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
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
            updatedAt = model.updatedAt,
            fee = model.fee,
            feeType = Helpers.feeTypeDecider(model.feeType)
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
            updatedAt = domain.updatedAt,
            fee = domain.fee,
            feeType = Helpers.feeTypeChanger(domain.feeType)
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
            updatedAt = model.updatedAt,
            feeType = Helpers.feeTypeDecider(model.feeType),
            fee = model.fee
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
            updatedAt = domain.updatedAt,
            fee = domain.fee,
            feeType = Helpers.feeTypeChanger(domain.feeType)
        )
    }
}

class BalanceSheetMapper : Mapper<BalanceSheet, BalanceSheetDomain> {
    override fun mapToDomain(model: BalanceSheet): BalanceSheetDomain {
        return BalanceSheetDomain(
            id = model.balanceSheetId,
            accountNo = model.accountNo,
            accountName = Helpers.stringToAccountName(model.accountName),
            balance = model.accountBalance,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapToModel(domain: BalanceSheetDomain): BalanceSheet {
        return BalanceSheet(
            balanceSheetId = domain.id,
            accountNo = domain.accountNo,
            accountName = Helpers.accountNameToString(domain.accountName),
            accountBalance = domain.balance,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

class BalanceSheetLocalMapper(
    private val gson: Gson
): Mapper<BalanceSheetLocal, BalanceSheetDomain> {
    override fun mapToDomain(model: BalanceSheetLocal): BalanceSheetDomain {
        val balance = gson.fromJson(model.balance, AccountBalance::class.java)

        return BalanceSheetDomain(
            id = model.id,
            accountNo = model.accountNo,
            accountName = Helpers.stringToAccountName(model.accountName),
            balance = balance,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapToModel(domain: BalanceSheetDomain): BalanceSheetLocal {
        val balanceJson = gson.toJson(domain.balance)

        return BalanceSheetLocal(
            id = domain.id,
            accountNo = domain.accountNo,
            accountName = Helpers.accountNameToString(domain.accountName),
            balance = balanceJson,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}

class CashReceiptJournalMapper(
    private val gson: Gson
) : Mapper<CashReceiptJournalLocal, CashReceiptJournal> {
    override fun mapToDomain(model: CashReceiptJournalLocal): CashReceiptJournal {
        val balance = gson.fromJson(model.balance, AccountBalance::class.java)

        return CashReceiptJournal(
            id = model.id,
            date = model.date,
            description = model.description,
            balance = balance
        )
    }

    override fun mapToModel(domain: CashReceiptJournal): CashReceiptJournalLocal {
        val balanceJson = gson.toJson(domain.balance)

        return CashReceiptJournalLocal(
            id = domain.id,
            date = domain.date,
            description = domain.description,
            balance = balanceJson
        )
    }

}