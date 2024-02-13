package com.jhoangamarral.ticketmasterchallenge.data.repository

import androidx.paging.PagingSource
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventDao
import com.jhoangamarral.ticketmasterchallenge.data.db.events.EventRemoteKeyDao
import com.jhoangamarral.ticketmasterchallenge.data.entities.EventDbData
import com.jhoangamarral.ticketmasterchallenge.data.entities.EventRemoteKeyDbData
import com.jhoangamarral.ticketmasterchallenge.data.entities.toDomain
import com.jhoangamarral.ticketmasterchallenge.data.exception.DataNotAvailableException
import com.jhoangamarral.ticketmasterchallenge.data.mapper.toDbData
import com.jhoangamarral.ticketmasterchallenge.data.util.DiskExecutor
import com.jhoangamarral.ticketmasterchallenge.domain.entities.EventEntity
import com.jhoangamarral.ticketmasterchallenge.domain.util.Result

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

class EventLocalDatasource (
    private val executor: DiskExecutor,
    private val eventDao: EventDao,
    private val remoteKeyDao: EventRemoteKeyDao,
) : EventDataSource.Local {

    override fun events(): PagingSource<Int, EventDbData> = eventDao.events()

    override suspend fun getEvents(): Result<List<EventEntity>> = withContext(executor.asCoroutineDispatcher()) {
        val events = eventDao.getEvents()
        return@withContext if (events.isNotEmpty()) {
            Result.Success(events.map { it.toDomain() })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun saveEvents(eventEntities: List<EventEntity>) = withContext(executor.asCoroutineDispatcher()) {
        eventDao.saveEvents(eventEntities.map { it.toDbData() })
    }

    override suspend fun getLastRemoteKey(): EventRemoteKeyDbData? = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: EventRemoteKeyDbData) = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.saveRemoteKey(key)
    }

    override suspend fun clearEvents() = withContext(executor.asCoroutineDispatcher()) {
        eventDao.clearEventsExceptFavorites()
    }

    override suspend fun clearRemoteKeys() = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.clearRemoteKeys()
    }
}