package com.jhoangamarral.ticketmasterchallenge.presentation.ui.search

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jhoangamarral.ticketmasterchallenge.R
import com.jhoangamarral.ticketmasterchallenge.presentation.LocalNavControllerProvider
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.navigation.Page
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.EmptyStateView
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.EventList
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.LoaderFullScreen
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.SearchView
import com.jhoangamarral.ticketmasterchallenge.presentation.util.PreviewContainer
import kotlinx.coroutines.flow.flowOf


fun NavGraphBuilder.searchScreen() = composable(route = Page.Search.route) {

    val navController = LocalNavControllerProvider.current
    val searchViewModel: SearchViewModel = hiltViewModel(it)
    val uiState by searchViewModel.uiState.collectAsState()
    val events = searchViewModel.events.collectAsLazyPagingItems()
    searchViewModel.onLoadStateUpdate(events.loadState, events.itemCount)

    SearchScreen(
        searchUiState = uiState,
        events = events,
        onQueryChange = searchViewModel::onSearch,
        onBackClick = { navController.popBackStack() }
    )

}

@Composable
fun SearchScreen(
    searchUiState: SearchViewModel.SearchUiState,
    events: LazyPagingItems<EventListItem>,
    onQueryChange: (query: String) -> Unit,
    onBackClick: () -> Unit
) {
    Surface {
        val context = LocalContext.current
        val showDefaultState = searchUiState.showDefaultState
        val showNoEventsFound = searchUiState.showNoEventsFound
        val isLoading = searchUiState.showLoading
        val errorMessage = searchUiState.errorMessage

        if (errorMessage != null) Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

        Scaffold(topBar = {
            SearchView(onQueryChange, onBackClick)
        }) {
            Box(modifier = Modifier.padding(it)) {
                if (!showDefaultState) {
                    if (isLoading) {
                        LoaderFullScreen(
                            alignment = Alignment.TopCenter,
                            modifier = Modifier.padding(top = 150.dp)
                        )
                    } else {
                        if (showNoEventsFound) {
                            EmptyStateView(
                                titleRes = R.string.no_events_found,
                                alignment = Alignment.TopCenter,
                                modifier = Modifier.padding(top = 150.dp)
                            )
                        } else {
                            EventList(events = events)
                        }
                    }
                }
            }

        }
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    val eventItems: List<EventListItem> = listOf(
        EventListItem.Event(
            "1",
            "Title 1",
            "https://i.stack.imgur.com/lDFzt.jpg",
            "Los Angeles",
            "09/10/24"
        ),
        EventListItem.Event(
            "2",
            "Title 2",
            "https://i.stack.imgur.com/lDFzt.jpg",
            "Los Angeles 2",
            "09/10/25"
        )
        )

    PreviewContainer {
        val events = flowOf(PagingData.from(eventItems)).collectAsLazyPagingItems()
        SearchScreen(
            SearchViewModel.SearchUiState(
                showDefaultState = false,
                showNoEventsFound = true
            ), events, {}, {})
    }
}