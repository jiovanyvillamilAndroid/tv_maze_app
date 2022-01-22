package com.crisvillamil.tvmazeapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.databinding.EpisodesItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse

class EpisodesAdapter(private val episodesData: List<EpisodeResponse>) :
    RecyclerView.Adapter<EpisodesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder =
        EpisodesViewHolder(
            EpisodesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) =
        holder.bindEpisode(episodesData[position])

    override fun getItemCount(): Int = episodesData.size
}