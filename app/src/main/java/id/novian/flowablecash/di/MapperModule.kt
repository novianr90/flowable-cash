package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.domain.mapper.AccountMapper
import id.novian.flowablecash.domain.mapper.BalanceSheetLocalMapper
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.helpers.Mapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideBalanceSheetMapper(): Mapper<BalanceSheet, AccountDomain> = AccountMapper()

    @Provides
    fun provideBalanceSheetLocalMapper(
        gson: Gson
    ): Mapper<Accounts, AccountDomain> = BalanceSheetLocalMapper(gson)
}