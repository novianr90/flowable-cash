package id.novian.flowablecash.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.remote.models.purchase.Purchase
import id.novian.flowablecash.data.remote.models.sale.Sale
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.repository.PurchaseRemoteRepository
import id.novian.flowablecash.data.remote.repository.PurchaseRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.repository.SaleRemoteRepository
import id.novian.flowablecash.data.remote.repository.SaleRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.repository.TransactionRemoteRepository
import id.novian.flowablecash.data.remote.repository.TransactionRemoteRepositoryImpl
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import id.novian.flowablecash.domain.mapper.PurchaseMapper
import id.novian.flowablecash.domain.mapper.SaleMapper
import id.novian.flowablecash.domain.mapper.TransactionMapper
import id.novian.flowablecash.domain.models.PurchaseDomain
import id.novian.flowablecash.domain.models.SaleDomain
import id.novian.flowablecash.domain.models.TransactionDomain
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
    fun provideTransactionRepository(api: TransactionService): TransactionRemoteRepository {
        return TransactionRemoteRepositoryImpl(api)
    }

    @Provides
    fun provideSaleRepository(api: SaleService): SaleRemoteRepository {
        return SaleRemoteRepositoryImpl(api)
    }

    @Provides
    fun providePurchaseRepository(api: PurchaseService): PurchaseRemoteRepository {
        return PurchaseRemoteRepositoryImpl(api)
    }

    @Provides
    @Named("TRANSACTION_MAPPER")
    fun provideTransactionMapper(): Mapper<Transaction, TransactionDomain> = TransactionMapper()

    @Provides
    @Named("SALE_MAPPER")
    fun provideSaleMapper(): Mapper<Sale, SaleDomain> = SaleMapper()

    @Provides
    @Named("PURCHASE_MAPPER")
    fun providePurchaseMapper(): Mapper<Purchase, PurchaseDomain> = PurchaseMapper()
}