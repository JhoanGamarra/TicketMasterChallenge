package com.jhoangamarral.ticketmasterchallenge.presentation.ui.feed

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.feed.usecase.GetEvents
import com.jhoangamarral.ticketmasterchallenge.presentation.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class FeedViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getEvents: GetEvents

    @Mock
    lateinit var networkMonitor: NetworkMonitor

    private lateinit var viewModel: FeedViewModel

    private val events = listOf(EventListItem.Event("1234", "title 1", "imageUrl", "Sphere" , "09/10/18"))

    private val pagingData: Flow<PagingData<EventListItem>> = flowOf(PagingData.from(events))

    @Before
    fun setUp() {
        Mockito.`when`(getEvents.events(pageSize = Mockito.anyInt())).thenReturn(pagingData)
        viewModel = FeedViewModel(
            getEvents = getEvents,
            networkMonitor = networkMonitor,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onLoadStateUpdate_onLoading_showLoadingView() = runTest {
        viewModel.onLoadStateUpdate(getLoadState(LoadState.Loading))

        assertThat(viewModel.uiState.value.showLoading).isTrue()
    }

    @Test
    fun onLoadStateUpdate_onFailure_hideLoadingAndShowErrorMessage() = runTest {
        val errorMessage = "error"
        viewModel.onLoadStateUpdate(getLoadState(LoadState.Error(Throwable(errorMessage))))

        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun onLoadStateUpdate_onSuccess_hideLoadingAndShowEvents() = runTest {
        viewModel.onLoadStateUpdate(getLoadState(LoadState.NotLoading(true)))

        // TODO - test events flow
        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isNull()
        }
    }

    private fun getLoadState(state: LoadState): CombinedLoadStates =
        CombinedLoadStates(state, state, state, LoadStates(state, state, state))
    

}