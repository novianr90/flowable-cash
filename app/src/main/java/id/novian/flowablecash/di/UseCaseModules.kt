package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.repository.UpdateModelBalanceSheetLocalRepository
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import id.novian.flowablecash.usecase.posting.PostingUseCase
import id.novian.flowablecash.usecase.posting.PostingUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModules {

    @Singleton
    @Provides
    fun providePostingUseCase(
        gson: Gson,
        updateModelBalanceSheetLocalRepository: UpdateModelBalanceSheetLocalRepository,
        balanceSheetRepository: BalanceSheetRepository
        ): PostingUseCase {
        return PostingUseCaseImpl(
            gson = gson,
            local = updateModelBalanceSheetLocalRepository,
            remote = balanceSheetRepository
        )
    }
}