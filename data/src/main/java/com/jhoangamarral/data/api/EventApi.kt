package com.jhoangamarral.data.api

import com.jhoangamarral.data.entities.EventRequestApi
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {

    @GET("/discovery/v2/events.json")
    suspend fun getEvents(
        @Query("page") page: Int,
        @Query("size") limit: Int,
        @Query("apikey") apikey: String = "DW0E98NrxUIfDDtNN7ijruVSm60ryFLX"
    ): EventRequestApi

    @GET("/discovery/v2/events.json")
    suspend fun search(
        @Query("keyword") query: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String ="DW0E98NrxUIfDDtNN7ijruVSm60ryFLX"
    ): EventRequestApi
}