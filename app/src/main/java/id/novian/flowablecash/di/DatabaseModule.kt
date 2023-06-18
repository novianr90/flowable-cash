package id.novian.flowablecash.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.data.local.database.AppDatabase
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepositoryImpl
import id.novian.flowablecash.data.local.repository.TransactionLocalRepository
import id.novian.flowablecash.data.local.repository.TransactionLocalRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "transaction-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLocalRepo(database: AppDatabase): TransactionLocalRepository {
        return TransactionLocalRepositoryImpl(database.dao())
    }

    @Provides
    fun provideBalanceLocalRepository(database: AppDatabase): BalanceSheetLocalRepository {
        return BalanceSheetLocalRepositoryImpl(database.balanceSheetDao())
    }
}