package com.jhoangamarral.data.events

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jhoangamarral.data.entities.EventDbData
import com.jhoangamarral.data.entities.EventRemoteKeyDbData

@Database(
    entities = [EventDbData::class, EventRemoteKeyDbData::class],
    version = 1,
    exportSchema = false
)

abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao() : EventDao
    abstract fun eventRemoteKeysDao(): EventRemoteKeyDao

}