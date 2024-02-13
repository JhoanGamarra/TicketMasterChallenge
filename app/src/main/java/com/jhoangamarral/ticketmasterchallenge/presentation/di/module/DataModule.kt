package com.jhoangamarral.ticketmasterchallenge.presentation.di.module

import com.jhoangamarral.data.api.EventApi
import com.jhoangamarral.data.events.EventDao
import com.jhoangamarral.data.events.EventRemoteKeyDao
import com.jhoangamarral.data.repository.*
import com.jhoangamarral.data.util.DiskExecutor
import com.jhoangamarral.domain.repository.EventRepository
import com.jhoangamarral.domain.usecases.SearchEvents
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
        return EventRepositoryImpl(
            eventRemote,
            eventLocal,
            eventRemoteMediator
        )
    }

    @Provides
    @Singleton
    fun provideEventLocalDataSource(
        executor: DiskExecutor,
        eventDao: EventDao,
        eventRemoteKeyDao: EventRemoteKeyDao,
    ): EventDataSource.Local {
        return EventLocalDatasource(
            executor,
            eventDao,
            eventRemoteKeyDao
        )
    }

    @Provides
    @Singleton
    fun provideEventMediator(
        eventLocalDataSource: EventDataSource.Local,
        eventRemoteDataSource: EventDataSource.Remote
    ): EventRemoteMediator {
        return EventRemoteMediator(
            eventLocalDataSource,
            eventRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideEventRemoveDataSource(eventApi: EventApi): EventDataSource.Remote {
        return EventRemoteDatasource(eventApi)
    }

    @Provides
    fun provideSearchEventsUseCase(eventRepository: EventRepository): SearchEvents {
        return SearchEvents(eventRepository)
    }


}