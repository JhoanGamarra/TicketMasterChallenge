package com.jhoangamarral.ticketmasterchallenge.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventDao
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventDatabase
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideEventDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(context, EventDatabase::class.java, "event.db").build()
    }

    @Provides
    fun provideEventDao(eventDatabase: EventDatabase): EventDao {
        return eventDatabase.eventDao()
    }

    @Provides
    fun provideMovieRemoteKeyDao(eventDatabase: EventDatabase): EventRemoteKeyDao {
        return eventDatabase.eventRemoteKeysDao()
    }
}