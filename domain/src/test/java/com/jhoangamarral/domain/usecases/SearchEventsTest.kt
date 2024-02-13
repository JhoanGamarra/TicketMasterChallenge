package com.jhoangamarral.domain.usecases

import androidx.paging.PagingData
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.repository.EventRepository
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


internal class SearchEventsTest {

    private lateinit var  eventRepository : EventRepository

    private lateinit var eventEntity :  EventEntity

    private lateinit var searchEvents: SearchEvents

    @Before
    fun setup(){
        eventRepository = Mockito.mock(EventRepository::class.java)
        eventEntity = Mockito.mock(EventEntity::class.java)
        searchEvents = SearchEvents(eventRepository)
    }


    @Test
    fun `when invoke is called then it should search event by keyword and return it`(){

        val stubData =   flowOf(PagingData.from(listOf(eventEntity)))
        Mockito.`when`(eventRepository.search("taylor", 1)).thenReturn(stubData)
        assertThat(searchEvents("taylor", 1), `is` (stubData))

    }


}