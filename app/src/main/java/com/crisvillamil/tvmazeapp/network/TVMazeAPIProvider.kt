package com.crisvillamil.tvmazeapp.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TVMazeAPIProvider {
    fun getTVMazeApi(): TVMazeAPI {
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://api.tvmaze.com/")
            addConverterFactory(MoshiConverterFactory.create())
        }.build()
        return retrofit.create(TVMazeAPI::class.java)
    }
}