package com.crisvillamil.tvmazeapp.network

import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.model.ShowFindResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeAPI {
    @GET("/shows")
    suspend fun getAllShows(@Query("page") page: Int): Response<List<Show>>

    @GET("/search/shows")
    suspend fun searchShows(@Query("q") searchText: String): Response<List<ShowFindResponse>>

    @GET("/shows/{show_id}/seasons")
    suspend fun getSeasons(@Path(value = "show_id") showId: Int): Response<List<SeasonResponse>>

    @GET("/seasons/{seasons_id}/episodes")
    suspend fun getEpisodes(@Path(value = "seasons_id") seasonId: Int): Response<List<EpisodeResponse>>
}