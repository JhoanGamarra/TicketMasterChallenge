package com.jhoangamarral.ticketmasterchallenge.presentation.di.module

import android.content.Context
import com.jhoangamarral.data.util.DiskExecutor
import com.jhoangamarral.data.util.DispatchersProvider
import com.jhoangamarral.data.util.DispatchersProviderImpl
import com.jhoangamarral.ticketmasterchallenge.presentation.util.NetworkMonitor
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
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitor(context)
}