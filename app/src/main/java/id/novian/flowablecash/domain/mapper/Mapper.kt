package id.novian.flowablecash.domain.mapper


import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Helpers.accountNameToString
import id.novian.flowablecash.helpers.Helpers.stringToAccountName
import id.novian.flowablecash.helpers.Mapper

class TransactionMapper : Mapper<Transaction, TransactionDomain> {
    override fun mapToDomain(model: Transaction): TransactionDomain {
        return TransactionDomain(
            id = model.id,
            transactionName = model.name,
            transactionDate = model.date,
            transactionType = Helpers.transactionTypeDecider(model.type),
            total = model.total,
            transactionDescription = model.description,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            fee = model.fee,
            feeType = Helpers.feeTypeDecider(model.feeType),
            payment = model.payment,
            alreadyPosted = model.alreadyPosted
        )
    }

    override fun mapToModel(domain: TransactionDomain): Transaction {
        return Transaction(
            id = domain.id,
            name = domain.transactionName,
            date = domain.transactionDate,
            type = Helpers.transactionTypeChanger(domain.transactionType),
            total = domain.total,
            description = domain.transactionDescription,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            fee = domain.fee,
            feeType = Helpers.feeTypeChanger(domain.feeType),
            payment = domain.payment,
            alreadyPosted = domain.alreadyPosted
        )
    }
}

class LocalMapper : Mapper<TransactionLocal, TransactionDomain> {
    override fun mapToDomain(model: TransactionLocal): TransactionDomain {
        return TransactionDomain(
            id = model.id,
            transactionName = model.transactionName,
            transactionDate = model.transactionDate,
            transactionType = Helpers.transactionTypeDecider(model.transactionType),
            total = model.transactionTotal,
            transactionDescription = model.transactionDescription,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            feeType = Helpers.feeTypeDecider(model.feeType),
            fee = model.fee,
            payment = model.payment,
            alreadyPosted = model.alreadyPosted
        )
    }

    override fun mapToModel(domain: TransactionDomain): TransactionLocal {
        return TransactionLocal(
            id = domain.id,
            transactionName = domain.transactionName,
            transactionDate = domain.transactionDate,
            transactionType = Helpers.transactionTypeChanger(domain.transactionType),
            transactionTotal = domain.total,
            transactionDescription = domain.transactionDescription,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            fee = domain.fee,
            feeType = Helpers.feeTypeChanger(domain.feeType),
            payment = domain.payment,
            alreadyPosted = domain.alreadyPosted
        )
    }
}

class AccountMapper : Mapper<BalanceSheet, AccountDomain> {
    override fun mapToDomain(model: BalanceSheet): AccountDomain {
        return AccountDomain(
            id = model.balanceSheetId,
            accountNo = model.accountNo,
            accountName = stringToAccountName(model.accountName),
            balance = model.accountBalance,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            month = model.month
        )
    }

    override fun mapToModel(domain: AccountDomain): BalanceSheet {
        return BalanceSheet(
            balanceSheetId = domain.id,
            accountNo = domain.accountNo,
            accountName = accountNameToString(domain.accountName),
            accountBalance = domain.balance,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            month = domain.month
        )
    }
}

class BalanceSheetLocalMapper(
    private val gson: Gson
): Mapper<Accounts, AccountDomain> {
    override fun mapToDomain(model: Accounts): AccountDomain {
        val balance = gson.fromJson(model.balance, AccountBalance::class.java)

        return AccountDomain(
            id = model.id,
            accountNo = model.accountNo,
            accountName = stringToAccountName(model.accountName),
            balance = balance,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            month = model.month
        )
    }

    override fun mapToModel(domain: AccountDomain): Accounts {
        val balanceJson = gson.toJson(domain.balance)

        return Accounts(
            id = domain.id,
            accountNo = domain.accountNo,
            accountName = accountNameToString(domain.accountName),
            balance = balanceJson,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            month = domain.month
        )
    }
}