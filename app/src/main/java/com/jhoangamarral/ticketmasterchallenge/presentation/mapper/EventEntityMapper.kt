package com.jhoangamarral.ticketmasterchallenge.presentation.mapper

import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem


fun EventEntity.toPresentation() = EventListItem.Event(
    id = id,
    imageUrl = imageUrl,
    startDate = startDate,
    title = title,
    venue = venue
)