package com.jhoangamarral.domain.entities

data class EventEntity(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val venue : String?,
    val startDate: String?
)

