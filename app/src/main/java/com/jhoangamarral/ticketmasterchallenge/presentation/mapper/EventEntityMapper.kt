package com.jhoangamarral.ticketmasterchallenge.presentation.mapper

import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem

fun EventEntity.toPresentation() = EventListItem(
    id = id,
    imageUrl = imageUrl,
    startDate = startDate,
    title = title,
    venue = venue
)