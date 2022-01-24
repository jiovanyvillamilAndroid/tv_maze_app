package com.crisvillamil.tvmazeapp.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TVMazeAPIProvider {

    private const val BASE_URL = "https://api.tvmaze.com/"
    fun getTVMazeApi(): TVMazeAPI {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(MoshiConverterFactory.create())
        }.build()
        return retrofit.create(TVMazeAPI::class.java)
    }
}