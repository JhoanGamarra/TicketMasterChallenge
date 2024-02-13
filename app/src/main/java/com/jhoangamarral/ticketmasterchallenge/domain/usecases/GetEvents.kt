package com.jhoangamarral.ticketmasterchallenge.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.jhoangamarral.ticketmasterchallenge.domain.repository.EventRepository
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.mapper.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEvents @Inject constructor(private val eventRepository: EventRepository) {

    fun events(pageSize: Int): Flow<PagingData<EventListItem>> =
        eventRepository.events(pageSize).map {
            it.map { event -> event.toPresentation() }
        }

}
