package com.jhoangamarral.ticketmasterchallenge.data.repository

import androidx.paging.PagingSource
import com.jhoangamarral.ticketmasterchallenge.data.entities.EventDbData
import com.jhoangamarral.ticketmasterchallenge.data.entities.EventRemoteKeyDbData
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity
import com.jhoangamarral.ticketmasterchallenge.domain.util.Result



interface EventDataSource {

    interface Remote {
        suspend fun getEvents(page: Int, limit: Int): Result<List<EventEntity>>
        suspend fun search(query: String, page: Int, limit: Int): Result<List<EventEntity>>
    }

    interface Local {
        fun events(): PagingSource<Int, EventDbData>
        suspend fun getEvents(): Result<List<EventEntity>>
        suspend fun saveEvents(eventEntities: List<EventEntity>)
        suspend fun getLastRemoteKey(): EventRemoteKeyDbData?
        suspend fun saveRemoteKey(key: EventRemoteKeyDbData)
        suspend fun clearRemoteKeys()
        suspend fun clearEvents()
    }

}