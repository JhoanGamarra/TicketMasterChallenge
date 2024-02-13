package com.jhoangamarral.ticketmasterchallenge.presentation.ui.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jhoangamarral.ticketmasterchallenge.presentation.navigation.LocalNavControllerProvider
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.navigation.Page
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.EventList
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.LoaderFullScreen
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.components.TopBar
import com.jhoangamarral.ticketmasterchallenge.presentation.util.PreviewContainer
import kotlinx.coroutines.flow.flowOf

fun NavGraphBuilder.feedScreen() = composable(route = Page.Feed.route) {

    val feedViewModel: FeedViewModel = hiltViewModel(it)
    val eventsPaging = feedViewModel.events.collectAsLazyPagingItems()
    val uiState by feedViewModel.uiState.collectAsState()
    val networkState by feedViewModel.networkState.collectAsState(null)

    LaunchedEffect(key1 = eventsPaging.loadState) {
        feedViewModel.onLoadStateUpdate(eventsPaging.loadState)
    }

    FeedScreen(eventsPaging, uiState)

}

@Composable
private fun FeedScreen(
    events: LazyPagingItems<EventListItem>,
    uiState: FeedViewModel.FeedUiState,
) {
    val navController = LocalNavControllerProvider.current
    Scaffold(
        topBar = {
            TopBar(
                "Ticket Master App",
                onSearchClick = {
                    navController.navigate(Page.Search.route)
                }
            )

        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            Surface {
                if (uiState.showLoading) {
                    LoaderFullScreen()
                } else {
                    EventList(events)
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_3, showSystemUi = true)
@Composable
private fun FeedScreenPreview() {
    val events = flowOf(PagingData.from(listOf<EventListItem>())).collectAsLazyPagingItems()
    PreviewContainer {
        FeedScreen(events, FeedViewModel.FeedUiState())
    }
}