package com.jhoangamarral.ticketmasterchallenge.data.entities

import com.google.gson.annotations.SerializedName
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity


data class EventData(
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<EventImage>?,
    @SerializedName("name") val title: String,
    @SerializedName("dates") val dates: EventDate?,
    @SerializedName("_embedded") val embedded: Embedded?
    )

data class EventImage(@SerializedName("url") val url : String)

data class EventDate(val start : StartDate)

data class StartDate(val localDate : String)

data class Embedded(val venues : List<Venue>)

data class Venue(val name : String)

fun EventData.toDomain() = EventEntity(
    id = id,
    imageUrl = images?.first()?.url,
    title = title,
    startDate = dates?.start?.localDate,
    venue = embedded?.venues?.first()?.name
)