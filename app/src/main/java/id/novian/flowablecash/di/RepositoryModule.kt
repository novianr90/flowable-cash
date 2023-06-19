package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.database.AppDatabase
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepositoryImpl
import id.novian.flowablecash.data.local.repository.CashReceiptJournalLocalRepository
import id.novian.flowablecash.data.local.repository.CashReceiptJournalLocalRepositoryImpl
import id.novian.flowablecash.data.local.repository.TransactionLocalRepository
import id.novian.flowablecash.data.local.repository.TransactionLocalRepositoryImpl
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.data.remote.repository.MainRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.domain.repository.BalanceSheetRepositoryImpl
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepositoryImpl
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.domain.repository.TransactionRepositoryImpl
import id.novian.flowablecash.helpers.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepo(database: AppDatabase): TransactionLocalRepository {
        return TransactionLocalRepositoryImpl(database.dao())
    }

    @Provides
    @Singleton
    fun provideBalanceLocalRepository(database: AppDatabase): BalanceSheetLocalRepository {
        return BalanceSheetLocalRepositoryImpl(database.balanceSheetDao())
    }

    @Provides
    @Singleton
    fun provideCashReceiptJournalLocalRepository(database: AppDatabase): CashReceiptJournalLocalRepository {
        return CashReceiptJournalLocalRepositoryImpl(database.cashReceiptDao())
    }

    @Provides
    @Singleton
    fun provideMainRemoteRepository(
        trx: TransactionService,
        sale: SaleService,
        purchase: PurchaseService,
        balanceSheet: BalanceSheetService
    ): MainRemoteRepository = MainRemoteRepositoryImpl(purchase, sale, trx, balanceSheet)

    @Singleton
    @Provides
    fun provideTransactionRepository(
        remoteRepository: MainRemoteRepository,
        localRepository: TransactionLocalRepository,
        remoteMapper: Mapper<Transaction, TransactionDomain>,
        localMapper: Mapper<TransactionLocal, TransactionDomain>
    ): TransactionRepository =
        TransactionRepositoryImpl(remoteRepository, localRepository, remoteMapper, localMapper)

    @Singleton
    @Provides
    fun provideBalanceSheetRepository(
        remote: MainRemoteRepository,
        local: BalanceSheetLocalRepository,
        remoteMapper: Mapper<BalanceSheet, BalanceSheetDomain>,
        localMapper: Mapper<BalanceSheetLocal, BalanceSheetDomain>,
        gson: Gson
    ): BalanceSheetRepository {
        return BalanceSheetRepositoryImpl(
            local = local,
            remote = remote,
            localMapper = localMapper,
            remoteMapper = remoteMapper,
            gson = gson
        )
    }

    @Singleton
    @Provides
    fun provideCashReceiptJournalRepo(
        localRepo: CashReceiptJournalLocalRepository,
        sale: TransactionRepository,
        mapper: Mapper<CashReceiptJournalLocal, CashReceiptJournal>,
        gson: Gson
    ): CashReceiptJournalRepository {
        return CashReceiptJournalRepositoryImpl(
            localRepo = localRepo,
            sale = sale,
            mapper = mapper,
            gson = gson
        )
    }
}