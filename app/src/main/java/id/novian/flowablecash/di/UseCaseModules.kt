package id.novian.flowablecash.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.remote.repository.PostingRepository
import id.novian.flowablecash.domain.repository.AccountsRepository
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
        accountsRepository: AccountsRepository,
        postingRepository: PostingRepository
        ): PostingUseCase {
        return PostingUseCaseImpl(
            remote = accountsRepository,
            postings = postingRepository
        )
    }
}