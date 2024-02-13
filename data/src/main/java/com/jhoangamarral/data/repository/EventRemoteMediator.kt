package com.jhoangamarral.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.jhoangamarral.data.entities.EventDbData
import com.jhoangamarral.data.entities.EventRemoteKeyDbData
import com.jhoangamarral.domain.util.getResult


private const val EVENT_STARTING_PAGE_INDEX = 1


@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val local: EventDataSource.Local,
    private val remote: EventDataSource.Remote
) : RemoteMediator<Int, EventDbData>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, EventDbData>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> EVENT_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastRemoteKey()?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        Log.d("XXX", "EventRemoteMediator: load() called with: loadType = $loadType, page: $page, stateLastItem = ${state.isEmpty()}")

        // There was a lag in loading the first page; as a result, it jumps to the end of the pagination.
        if (state.isEmpty() && page == 2) return MediatorResult.Success(endOfPaginationReached = false)

        remote.getEvents(page, state.config.pageSize).getResult({ successResult ->
            Log.d("XXX", "EventsRemoteMediator: get events from remote")
            if (loadType == LoadType.REFRESH) {
                local.clearEvents()
                local.clearRemoteKeys()
            }

            val events = successResult.data

            val endOfPaginationReached = events.isEmpty()

            val prevPage = if (page == EVENT_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            val key = EventRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)

            local.saveEvents(events)
            local.saveRemoteKey(key)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }, { errorResult ->
            return MediatorResult.Error(errorResult.error)
        })
    }
}