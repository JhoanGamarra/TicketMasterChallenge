package com.jhoangamarral.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhoangamarral.domain.entities.EventEntity

@Entity(tableName = "events")
data class EventDbData(
    @PrimaryKey val id: String,
    val title : String,
    val image : String?,
    val venue : String?,
    val date : String?
)

fun EventDbData.toDomain() = EventEntity(
    id = id,
    title = title,
    venue = venue,
    imageUrl = image,
    startDate = date
)
