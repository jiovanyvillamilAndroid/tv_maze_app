package com.crisvillamil.tvmazeapp.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.R
import com.crisvillamil.tvmazeapp.databinding.SeasonItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.view.bindOrHide

class SeasonViewHolder(private val seasonItemBinding: SeasonItemBinding) :
    RecyclerView.ViewHolder(seasonItemBinding.root) {

    fun bindSeason(season: SeasonResponse, episodes: List<EpisodeResponse>?) {

        seasonItemBinding.seasonsId.bindOrHide(
            seasonItemBinding.root.context.resources.getString(
                R.string.seasons_name,
                season.number
            )
        )
        episodes?.let {
            seasonItemBinding.episodesRecyclerView.adapter = EpisodesAdapter(episodes)
        }
    }
}