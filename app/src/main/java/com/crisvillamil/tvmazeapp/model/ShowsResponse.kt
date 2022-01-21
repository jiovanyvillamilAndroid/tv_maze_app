package com.crisvillamil.tvmazeapp.model

data class Show(
    val id: Int,
    val name: String,
    val language: String,
    val genres: List<String>,
    val image: ImageResponse
)

data class ImageResponse(
    val medium: String,
    val original: String
)
