package com.jhoangamarral.ticketmasterchallenge.domain.usecases

import androidx.paging.PagingData
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity
import com.jhoangamarral.ticketmasterchallenge.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchEvents @Inject constructor(private val eventRepository: EventRepository) {

    operator fun invoke(query: String, pageSize: Int): Flow<PagingData<EventEntity>> = eventRepository.search(query, pageSize)

}