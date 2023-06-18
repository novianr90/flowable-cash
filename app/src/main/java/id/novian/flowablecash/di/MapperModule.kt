package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.domain.mapper.BalanceSheetLocalMapper
import id.novian.flowablecash.domain.mapper.BalanceSheetMapper
import id.novian.flowablecash.domain.mapper.CashReceiptJournalMapper
import id.novian.flowablecash.domain.mapper.LocalMapper
import id.novian.flowablecash.domain.mapper.TransactionMapper
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Mapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideTransactionMapper(): Mapper<Transaction, TransactionDomain> = TransactionMapper()

    @Provides
    fun provideLocalMapper(): Mapper<TransactionLocal, TransactionDomain> = LocalMapper()

    @Provides
    fun provideBalanceSheetMapper(): Mapper<BalanceSheet, BalanceSheetDomain> = BalanceSheetMapper()

    @Provides
    fun provideBalanceSheetLocalMapper(
        gson: Gson
    ): Mapper<BalanceSheetLocal, BalanceSheetDomain> = BalanceSheetLocalMapper(gson)

    @Provides
    fun provideCashReceiptJournalMapper(
        gson: Gson
    ): Mapper<CashReceiptJournalLocal, CashReceiptJournal> = CashReceiptJournalMapper(gson)
}