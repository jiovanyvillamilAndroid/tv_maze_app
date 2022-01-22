package com.crisvillamil.tvmazeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.network.TVMazeAPIProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowDetailViewModel : ViewModel() {

    suspend fun getSeasons(showId: Int): List<SeasonResponse>? {
        return withContext(Dispatchers.IO) {
            TVMazeAPIProvider.getTVMazeApi().getSeasons(showId).body()
        }
    }

    suspend fun getEpisodes(seasonId: Int): List<EpisodeResponse>? {
        return withContext(Dispatchers.IO) {
            TVMazeAPIProvider.getTVMazeApi().getEpisodes(seasonId).body()
        }
    }
}