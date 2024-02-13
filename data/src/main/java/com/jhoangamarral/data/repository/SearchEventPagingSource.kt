package com.jhoangamarral.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.util.getResult

private const val STARTING_PAGE_INDEX = 1

class SearchEventPagingSource(
    private val query: String,
    private val remote: EventDataSource.Remote
) : PagingSource<Int, EventEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventEntity> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return remote.search(query, page).getResult({
            LoadResult.Page(
                data = it.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (it.data.isEmpty()) null else page + 1
            )
        }, {
            LoadResult.Error(it.error)
        })
    }

    override fun getRefreshKey(state: PagingState<Int, EventEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}