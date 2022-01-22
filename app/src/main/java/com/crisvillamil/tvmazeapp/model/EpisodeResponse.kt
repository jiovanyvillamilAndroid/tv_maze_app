package com.crisvillamil.tvmazeapp.model

data class EpisodeResponse(
    val id: Int,
    val name: String,
    val season: Int,
    val image: ImageResponse?,
    val summary: String
)