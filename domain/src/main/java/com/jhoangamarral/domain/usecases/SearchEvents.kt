package com.jhoangamarral.domain.usecases

import androidx.paging.PagingData
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class SearchEvents (private val eventRepository: EventRepository) {

    operator fun invoke(query: String, pageSize: Int): Flow<PagingData<EventEntity>> = eventRepository.search(query, pageSize)

}