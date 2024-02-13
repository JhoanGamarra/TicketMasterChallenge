package com.jhoangamarral.data.mapper

import com.jhoangamarral.data.entities.EventDbData
import com.jhoangamarral.domain.entities.EventEntity

fun EventEntity.toDbData() = EventDbData(
    id = id,
    image = imageUrl,
    venue = venue,
    date = startDate,
    title = title
)