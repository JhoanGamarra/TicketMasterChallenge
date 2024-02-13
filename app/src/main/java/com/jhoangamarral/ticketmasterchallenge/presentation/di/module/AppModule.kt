package com.jhoangamarral.ticketmasterchallenge.presentation.di.module

import android.content.Context
import android.content.SharedPreferences
import com.jhoangamarral.ticketmasterchallenge.data.util.DiskExecutor
import com.jhoangamarral.ticketmasterchallenge.data.util.DispatchersProvider
import com.jhoangamarral.ticketmasterchallenge.data.util.DispatchersProviderImpl
import com.jhoangamarral.ticketmasterchallenge.presentation.di.AppSettingsSharedPreference
import com.jhoangamarral.ticketmasterchallenge.presentation.util.NetworkMonitor
import com.jhoangamarral.ticketmasterchallenge.presentation.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()
    }

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl
    }

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @AppSettingsSharedPreference
    fun provideAppSettingsSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitor(context)
}