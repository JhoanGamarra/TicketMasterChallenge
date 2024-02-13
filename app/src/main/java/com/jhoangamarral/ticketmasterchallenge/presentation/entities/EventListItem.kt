package com.jhoangamarral.ticketmasterchallenge.presentation.entities

data class EventListItem(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val venue : String?,
    val startDate: String?
)
