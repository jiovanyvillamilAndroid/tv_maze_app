package com.crisvillamil.tvmazeapp.network

import com.crisvillamil.tvmazeapp.model.Show
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowsApi {
    @GET("/shows")
    suspend fun getAllShows(@Query("page") page: Int): Response<List<Show>>
}