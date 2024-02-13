package com.jhoangamarral.data.repository

import androidx.paging.PagingData
import com.jhoangamarral.domain.entities.EventEntity
import com.jhoangamarral.domain.repository.EventRepository
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


internal class EventRepositoryTest {

    private lateinit var  eventRepository : EventRepository

    private lateinit var remoteDatasource: EventDataSource.Remote

    private lateinit var localDatasource: EventDataSource.Local

    private  lateinit var remoteMediator: EventRemoteMediator

    private lateinit var eventEntity : EventEntity


    @Before
    fun setup(){
        eventRepository = Mockito.mock(EventRepository::class.java)
        remoteDatasource = Mockito.mock(EventRemoteDatasource::class.java)
        localDatasource = Mockito.mock(EventLocalDatasource::class.java)
        remoteMediator = Mockito.mock(EventRemoteMediator::class.java)
        eventEntity = Mockito.mock(EventEntity::class.java)
    }


    @Test
    fun `when search method with the query parameter is called then it should search events by keyword and return it`(){

        val stubData =   flowOf(PagingData.from(listOf(eventEntity)))
        Mockito.`when`(eventRepository.search("taylor", 1)).thenReturn(stubData)
        MatcherAssert.assertThat(eventRepository.search("taylor", 1), Is.`is`(stubData))

    }

}