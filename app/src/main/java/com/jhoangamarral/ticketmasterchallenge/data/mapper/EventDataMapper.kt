package com.jhoangamarral.ticketmasterchallenge.data.mapper

import com.jhoangamarral.ticketmasterchallenge.data.entities.EventDbData
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity

fun EventEntity.toDbData() = EventDbData(
    id = id,
    image = imageUrl,
    venue = venue,
    date = startDate,
    title = title
)