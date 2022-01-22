package com.crisvillamil.tvmazeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: Int,
    val name: String,
    val language: String,
    val genres: List<String>,
    val image: ImageResponse?,
    val ended: String?,
    val premiered: String,
    val summary: String
) : Parcelable

@Parcelize
data class ImageResponse(
    val medium: String,
    val original: String
) : Parcelable
