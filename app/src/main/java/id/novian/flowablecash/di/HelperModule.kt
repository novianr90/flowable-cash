package id.novian.flowablecash.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.helpers.FunctionInvocationManager
import id.novian.flowablecash.helpers.FunctionInvocationManagerImpl
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {

    @Singleton
    @Provides
    fun provideFunctionInvocationManager(
        context: Context,
        calendar: Calendar
    ): FunctionInvocationManager {
        return FunctionInvocationManagerImpl(context, calendar)
    }
}