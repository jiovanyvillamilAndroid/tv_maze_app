package com.crisvillamil.tvmazeapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.view.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.ShowItemBinding
import com.crisvillamil.tvmazeapp.model.Show

class ShowsAdapter(
    private val showsList: List<Show>,
    private val onItemSelected: OnItemSelected<Show>
) :
    RecyclerView.Adapter<ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder =
        ShowViewHolder(ShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) =
        holder.bindShow(showsList[position], onItemSelected)

    override fun getItemCount(): Int = showsList.size
}