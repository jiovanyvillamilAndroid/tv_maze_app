package com.crisvillamil.tvmazeapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.databinding.SeasonItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse

class SeasonsAdapter(
    private val data: List<SeasonResponse>,
    private val episodesHashMap: Map<Int, List<EpisodeResponse>?>
) :
    RecyclerView.Adapter<SeasonViewHolder>() {

    val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val itemBinding =
            SeasonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = SeasonViewHolder(itemBinding)
        itemBinding.episodesRecyclerView.setRecycledViewPool(viewPool)
        return viewHolder
    }


    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) =
        holder.bindSeason(data[position], episodesHashMap[data[position].id])

    override fun getItemCount(): Int = data.size

}