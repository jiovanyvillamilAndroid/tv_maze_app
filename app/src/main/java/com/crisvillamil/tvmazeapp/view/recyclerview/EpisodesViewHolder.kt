package com.crisvillamil.tvmazeapp.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.databinding.EpisodesItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.view.bindOrHide
import com.crisvillamil.tvmazeapp.view.loadImageFromURL

class EpisodesViewHolder(private val view: EpisodesItemBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun bindEpisode(episode: EpisodeResponse) {
        view.episodeName.bindOrHide(episode.name)
        view.imageEpisode.loadImageFromURL(episode.image?.medium)
    }
}