package id.novian.flowablecash.domain.mapper


import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.helpers.Helpers.accountNameToString
import id.novian.flowablecash.helpers.Helpers.stringToAccountName
import id.novian.flowablecash.helpers.Mapper

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