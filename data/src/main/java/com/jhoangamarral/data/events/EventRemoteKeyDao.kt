package com.jhoangamarral.data.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jhoangamarral.data.entities.EventRemoteKeyDbData

@Dao
interface EventRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKey(keys: EventRemoteKeyDbData)

    @Query("DELETE FROM events_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM events_remote_keys WHERE id = (SELECT MAX(id) FROM events_remote_keys)")
    suspend fun getLastRemoteKey(): EventRemoteKeyDbData?
}