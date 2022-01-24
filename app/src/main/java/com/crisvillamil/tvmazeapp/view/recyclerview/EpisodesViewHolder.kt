package com.crisvillamil.tvmazeapp.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.EpisodesItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.view.bindOrHide
import com.crisvillamil.tvmazeapp.view.loadImageFromURL

class EpisodesViewHolder(private val view: EpisodesItemBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun bindEpisode(episode: EpisodeResponse, onItemSelected: OnItemSelected<EpisodeResponse>) {
        bindEpisodeName(episode)
        view.imageEpisode.loadImageFromURL(episode.image?.original)
        view.root.setOnClickListener {
            onItemSelected.onItemSelected(episode, view.imageEpisode)
        }
    }

    private fun bindEpisodeName(episode: EpisodeResponse) {
        val episodeNumber = episode.number
        if (episodeNumber != null) {
            view.episodeName.bindOrHide("${episode.number}. ${episode.name}")
        } else {
            view.episodeName.bindOrHide(episode.name)
        }
    }
}