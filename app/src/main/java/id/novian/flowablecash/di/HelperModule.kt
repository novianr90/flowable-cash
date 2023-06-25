package id.novian.flowablecash.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.helpers.CalendarHelper
import id.novian.flowablecash.helpers.CalendarHelperImpl
import id.novian.flowablecash.helpers.FunctionInvocationManager
import id.novian.flowablecash.helpers.FunctionInvocationManagerImpl
import java.util.Calendar
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {

    @Singleton
    @Provides
    fun provideFunctionInvocationManager(
        context: Context,
        @Named("DEFAULT") calendar: Calendar
    ): FunctionInvocationManager {
        return FunctionInvocationManagerImpl(context, calendar)
    }

    @Singleton
    @Provides
    fun provideCalendarHelper(
        @Named("SORT") calendar: Calendar
    ): CalendarHelper = CalendarHelperImpl(calendar)
}