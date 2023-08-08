package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.database.AppDatabase
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.local.repository.AccountLocalRepository
import id.novian.flowablecash.data.local.repository.AccountLocalRepositoryImpl
import id.novian.flowablecash.data.local.repository.CashReceiptReportRepo
import id.novian.flowablecash.data.local.repository.CashReceiptReportRepoImpl
import id.novian.flowablecash.data.local.repository.CompanyDetailsRepository
import id.novian.flowablecash.data.local.repository.CompanyDetailsRepositoryImpl
import id.novian.flowablecash.data.local.repository.PurchasesReportRepo
import id.novian.flowablecash.data.local.repository.PurchasesReportRepoImpl
import id.novian.flowablecash.data.local.repository.TransactionLocalRepository
import id.novian.flowablecash.data.local.repository.TransactionLocalRepositoryImpl
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.data.remote.repository.MainRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.repository.PostingRepository
import id.novian.flowablecash.data.remote.repository.PostingRepositoryImpl
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PemasukkanService
import id.novian.flowablecash.data.remote.service.PengeluaranService
import id.novian.flowablecash.data.remote.service.PostingService
import id.novian.flowablecash.data.remote.service.TransactionService
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import id.novian.flowablecash.domain.repository.AccountsRepositoryImpl
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepositoryImpl
import id.novian.flowablecash.domain.repository.PurchasesJournalRepository
import id.novian.flowablecash.domain.repository.PurchasesJournalRepositoryImpl
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.domain.repository.TransactionRepositoryImpl
import id.novian.flowablecash.helpers.CalendarHelper
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
    fun provideBalanceLocalRepository(database: AppDatabase): AccountLocalRepository {
        return AccountLocalRepositoryImpl(database.accountsDao())
    }

    @Singleton
    @Provides
    fun providePurchasesReportLocalRepo(database: AppDatabase): PurchasesReportRepo {
        return PurchasesReportRepoImpl(database.purchasesDao())
    }

    @Singleton
    @Provides
    fun provideCashReceiptLocalRepo(database: AppDatabase): CashReceiptReportRepo {
        return CashReceiptReportRepoImpl(database.cashReceiptDao())
    }

    @Singleton
    @Provides
    fun provideCompanyDetailsRepo(database: AppDatabase): CompanyDetailsRepository {
        return CompanyDetailsRepositoryImpl(database.companyDetailsDao())
    }

    @Provides
    fun providePostingRepository(api: PostingService): PostingRepository = PostingRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideMainRemoteRepository(
        trx: TransactionService,
        sale: PemasukkanService,
        purchase: PengeluaranService,
        balanceSheet: BalanceSheetService,
        gson: Gson
    ): MainRemoteRepository = MainRemoteRepositoryImpl(purchase, sale, trx, balanceSheet, gson)

    @Singleton
    @Provides
    fun provideTransactionRepository(
        remoteRepository: MainRemoteRepository,
    ): TransactionRepository =
        TransactionRepositoryImpl(remoteRepository)

    @Singleton
    @Provides
    fun provideBalanceSheetRepository(
        remote: MainRemoteRepository,
        local: AccountLocalRepository,
        remoteMapper: Mapper<BalanceSheet, AccountDomain>,
        localMapper: Mapper<Accounts, AccountDomain>,
        gson: Gson
    ): AccountsRepository {
        return AccountsRepositoryImpl(
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
        repo: MainRemoteRepository,
        calendarHelper: CalendarHelper
    ): CashReceiptJournalRepository {
        return CashReceiptJournalRepositoryImpl(
            repo = repo,
            calendarHelper = calendarHelper
            )
    }

    @Singleton
    @Provides
    fun providePurchasesJournalRepository(
        repo: MainRemoteRepository,
        calendarHelper: CalendarHelper
        ): PurchasesJournalRepository {
        return PurchasesJournalRepositoryImpl(repo, calendarHelper)
    }
}