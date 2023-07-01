package id.novian.flowablecash.domain.mapper


import com.google.gson.Gson
import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
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
            feeType = Helpers.feeTypeDecider(model.feeType)
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
            transactionType = Helpers.transactionTypeDecider(model.transactionType),
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
            transactionType = Helpers.transactionTypeChanger(domain.transactionType),
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
            accountName = stringToAccountName(model.accountName),
            balance = model.accountBalance,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapToModel(domain: BalanceSheetDomain): BalanceSheet {
        return BalanceSheet(
            balanceSheetId = domain.id,
            accountNo = domain.accountNo,
            accountName = accountNameToString(domain.accountName),
            accountBalance = domain.balance,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }

    private fun stringToAccountName(value: String): AccountName {
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

    private fun accountNameToString(accountName: AccountName): String {
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