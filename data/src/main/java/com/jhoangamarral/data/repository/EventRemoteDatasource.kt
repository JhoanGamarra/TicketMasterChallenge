package com.jhoangamarral.data.repository

import com.jhoangamarral.data.api.EventApi
import com.jhoangamarral.data.entities.toDomain
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.util.Result


class EventRemoteDatasource(
    private val eventApi: EventApi
) : EventDataSource.Remote {

    override suspend fun getEvents(page: Int, limit: Int): Result<List<EventEntity>> = try {
        val result = eventApi.getEvents(page, limit)
        Result.Success(result.eventsList.data.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }


    override suspend fun search(query: String, page: Int, limit: Int): Result<List<EventEntity>> = try {
        val result = eventApi.search(query, page, limit)
        Result.Success(result.eventsList.data.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }
}
