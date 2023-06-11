package id.novian.flowablecash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.BuildConfig
import id.novian.flowablecash.data.remote.service.BalanceSheetService
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
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
    fun provideTransactionService(
        retrofit: Retrofit
    ): TransactionService {
        return retrofit.create(TransactionService::class.java)
    }

    @Provides
    fun provideSaleService(
        retrofit: Retrofit
    ): SaleService {
        return retrofit.create(SaleService::class.java)
    }

    @Provides
    fun providePurchaseService(
        retrofit: Retrofit
    ): PurchaseService {
        return retrofit.create(PurchaseService::class.java)
    }

    @Provides
    fun provideBalanceSheetService(retrofit: Retrofit): BalanceSheetService {
        return retrofit.create(BalanceSheetService::class.java)
    }
}