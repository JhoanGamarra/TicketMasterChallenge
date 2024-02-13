package com.jhoangamarral.ticketmasterchallenge.domain.repository

import androidx.paging.PagingData
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun events(pageSize: Int): Flow<PagingData<EventEntity>>
    fun search(query: String, pageSize: Int): Flow<PagingData<EventEntity>>
}