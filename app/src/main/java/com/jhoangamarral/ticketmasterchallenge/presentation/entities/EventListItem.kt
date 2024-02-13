package com.jhoangamarral.ticketmasterchallenge.presentation.entities

sealed class EventListItem {
    data class Event(
        val id: String,
        val title: String,
        val imageUrl: String?,
        val venue : String?,
        val startDate: String?
    ) : EventListItem()

}
