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
        if (result.pageData.totalElements > 0 && result.embedded != null){
            Result.Success(result.embedded.eventList.map { it.toDomain() })
        }else {
            Result.Success(emptyList())
        }
    } catch (e: Exception) {
        Result.Error(e)
    }


    override suspend fun search(query: String, page: Int): Result<List<EventEntity>> = try {
        val result = eventApi.search(query, page)
        if (result.pageData.totalElements > 0 && result.embedded != null){
            Result.Success(result.embedded.eventList.map { it.toDomain() })
        }else {
            Result.Success(emptyList())
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
