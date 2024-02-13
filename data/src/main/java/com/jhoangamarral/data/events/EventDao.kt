package com.jhoangamarral.data.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jhoangamarral.data.entities.EventDbData


@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun events(): PagingSource<Int, EventDbData>

    /**
     * Get all events from the event table.
     *
     * @return all events.
     */
    @Query("SELECT * FROM events")
    fun getEvents(): List<EventDbData>

    /**
     * Get event by id.
     * **/
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEvent(eventId: Int): EventDbData?

    /**
     * Insert all events.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvents(event: List<EventDbData>)

    /**
     * Delete all evetns except favorites.
     */
    @Query("DELETE FROM events")
    suspend fun clearEventsExceptFavorites()


}
