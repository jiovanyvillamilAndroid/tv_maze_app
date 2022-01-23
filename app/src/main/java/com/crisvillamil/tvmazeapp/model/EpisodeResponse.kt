package com.crisvillamil.tvmazeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeResponse(
    val id: Int,
    val name: String,
    val number: Int?,
    val season: Int,
    val image: ImageResponse?,
    val summary: String?
) : Parcelable