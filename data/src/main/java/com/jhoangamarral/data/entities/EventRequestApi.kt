package com.jhoangamarral.data.entities

import com.google.gson.annotations.SerializedName

data class EventRequestApi(@SerializedName("_embedded") val eventsList : EventListData)

data class EventListData(@SerializedName("events") val data : List<EventData>)
