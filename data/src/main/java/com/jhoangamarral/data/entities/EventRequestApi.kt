package com.jhoangamarral.data.entities

import com.google.gson.annotations.SerializedName

data class EventRequestApi(
    @SerializedName("_embedded") val embedded: EventListData?,
    @SerializedName("page") val pageData: PageData,
)

data class EventListData(@SerializedName("events") val eventList: List<EventData>)
data class PageData(@SerializedName("totalElements") val totalElements : Int )

