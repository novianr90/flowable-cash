package id.novian.flowablecash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.BuildConfig
import id.novian.flowablecash.data.remote.service.BalanceSheetService
import id.novian.flowablecash.data.remote.service.PostingService
import id.novian.flowablecash.data.remote.service.PurchaseService
import id.novian.flowablecash.data.remote.service.SaleService
import id.novian.flowablecash.data.remote.service.TransactionService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        url: String,
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionService(
        retrofit: Retrofit
    ): TransactionService {
        return retrofit.create(TransactionService::class.java)
    }

    @Provides
    @Singleton
    fun provideSaleService(
        retrofit: Retrofit
    ): SaleService {
        return retrofit.create(SaleService::class.java)
    }

    @Provides
    @Singleton
    fun providePurchaseService(
        retrofit: Retrofit
    ): PurchaseService {
        return retrofit.create(PurchaseService::class.java)
    }

    @Provides
    @Singleton
    fun provideBalanceSheetService(retrofit: Retrofit): BalanceSheetService {
        return retrofit.create(BalanceSheetService::class.java)
    }

    @Provides
    @Singleton
    fun providePostingService(retrofit: Retrofit): PostingService {
        return retrofit.create(PostingService::class.java)
    }
}