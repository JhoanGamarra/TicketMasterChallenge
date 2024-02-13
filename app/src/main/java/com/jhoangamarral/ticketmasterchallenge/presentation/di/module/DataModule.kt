package com.jhoangamarral.ticketmasterchallenge.presentation.di.module

import com.jhoangamarral.ticketmasterchallenge.data.api.EventApi
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventDao
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventRemoteKeyDao
import com.jhoangamarral.ticketmasterchallenge.data.repository.*
import com.jhoangamarral.ticketmasterchallenge.data.util.DiskExecutor
import com.jhoangamarral.ticketmasterchallenge.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideEventRepository(
        eventRemote: EventDataSource.Remote,
        eventLocal: EventDataSource.Local,
        eventRemoteMediator: EventRemoteMediator,
    ): EventRepository {
        return EventRepositoryImpl(eventRemote, eventLocal, eventRemoteMediator)
    }

    @Provides
    @Singleton
    fun provideEventLocalDataSource(
        executor: DiskExecutor,
        eventDao: EventDao,
        eventRemoteKeyDao: EventRemoteKeyDao,
    ): EventDataSource.Local {
        return EventLocalDatasource(executor, eventDao, eventRemoteKeyDao)
    }

    @Provides
    @Singleton
    fun provideEventMediator(
        eventLocalDataSource: EventDataSource.Local,
        eventRemoteDataSource: EventDataSource.Remote
    ): EventRemoteMediator {
        return EventRemoteMediator(eventLocalDataSource, eventRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideEventRemoveDataSource(eventApi: EventApi): EventDataSource.Remote {
        return EventRemoteDatasource(eventApi)
    }

}