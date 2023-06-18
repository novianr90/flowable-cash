package id.novian.flowablecash.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.CreateToastImpl
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

}