package com.jhoangamarral.ticketmasterchallenge.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_remote_keys")
data class EventRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)