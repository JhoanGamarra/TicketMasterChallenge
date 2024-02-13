package com.jhoangamarral.ticketmasterchallenge.presentation.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.jhoangamarral.ticketmasterchallenge.data.util.DispatchersProvider
import com.jhoangamarral.ticketmasterchallenge.domain.usecases.SearchEvents
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.mapper.toPresentation
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.base.BaseViewModel
import com.jhoangamarral.ticketmasterchallenge.presentation.util.singleSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchEvents: SearchEvents,
    private val savedStateHandle: SavedStateHandle,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class SearchUiState(
        val showDefaultState: Boolean = true,
        val showLoading: Boolean = false,
        val showNoEventsFound: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class NavigationState {
        data class EventDetails(val eventId: Int) : NavigationState()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    var events: Flow<PagingData<EventListItem>> = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
        .onEach { query ->
            _uiState.value = if (query.isNotEmpty()) SearchUiState(showDefaultState = false, showLoading = true) else SearchUiState()
        }
        .debounce(500)
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            searchEvents(query, 30).map { pagingData ->
                pagingData.map { eventEntity -> eventEntity.toPresentation() as EventListItem }
            }
        }.cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onSearch(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    fun onEventClicked(eventId: Int) =
        _navigationState.tryEmit(NavigationState.EventDetails(eventId))

    fun getSearchQuery(): CharSequence? = savedStateHandle.get<String>(KEY_SEARCH_QUERY)

    fun onLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update {
            it.copy(
                showLoading = showLoading,
                showNoEventsFound = showNoData,
                errorMessage = error
            )
        }
    }

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"
    }
}