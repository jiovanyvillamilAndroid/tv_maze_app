package com.crisvillamil.tvmazeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.network.TVMazeAPIProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ShowDetailViewModel : ViewModel() {

    private val itemsMapMutableLiveData: MutableLiveData<Pair<List<SeasonResponse>, Map<Int, List<EpisodeResponse>?>>> =
        MutableLiveData()
    val itemsMapLiveData = itemsMapMutableLiveData

    suspend fun getSeasons(showId: Int) {
        return withContext(Dispatchers.IO) {
            val seasons = TVMazeAPIProvider.getTVMazeApi().getSeasons(showId).body()
            if (seasons != null) {
                val subItems = seasons.map {
                    async { it.id to getEpisodes(it.id) }
                }.awaitAll()
                val itemsMap = subItems.map {
                    it.first to it.second
                }.toMap()
                withContext(Dispatchers.Main) {
                    itemsMapMutableLiveData.value = seasons to itemsMap
                }
            }
        }
    }

    private suspend fun getEpisodes(seasonId: Int): List<EpisodeResponse>? {
        return withContext(Dispatchers.IO) {
            TVMazeAPIProvider.getTVMazeApi().getEpisodes(seasonId).body()
        }
    }
}