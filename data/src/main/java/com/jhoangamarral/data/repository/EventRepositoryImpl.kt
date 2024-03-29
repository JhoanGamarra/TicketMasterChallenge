package com.jhoangamarral.data.repository

import androidx.paging.*
import com.jhoangamarral.data.entities.toDomain
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl constructor(
    private val remote: EventDataSource.Remote,
    private val local: EventDataSource.Local,
    private val remoteMediator: EventRemoteMediator,
) : EventRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun events(pageSize: Int): Flow<PagingData<EventEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.events() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<EventEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchEventPagingSource(query, remote) }
    ).flow

}
