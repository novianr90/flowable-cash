package id.novian.flowablecash.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.local.repository.TransactionLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.data.remote.repository.MainRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import id.novian.flowablecash.domain.mapper.BalanceSheetLocalMapper
import id.novian.flowablecash.domain.mapper.BalanceSheetMapper
import id.novian.flowablecash.domain.mapper.LocalMapper
import id.novian.flowablecash.domain.mapper.TransactionMapper
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.domain.repository.BalanceSheetRepositoryImpl
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.domain.repository.TransactionRepositoryImpl
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.CreateToastImpl
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideCreateToast(context: Context): CreateToast {
        return CreateToastImpl(context)
    }

    @Singleton
    @Provides
    @Named("IO")
    fun provideSchedulersIo(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @Named("MAIN")
    fun provideSchedulerMain(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideMainRemoteRepository(
        trx: TransactionService,
        sale: SaleService,
        purchase: PurchaseService,
        balanceSheet: BalanceSheetService
    ): MainRemoteRepository = MainRemoteRepositoryImpl(purchase, sale, trx, balanceSheet)

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
}