package com.crisvillamil.tvmazeapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.ShowItemBinding
import com.crisvillamil.tvmazeapp.model.Show

class ShowsPagingAdapter(private val onItemSelected: OnItemSelected<Show>) :
    PagingDataAdapter<Show, ShowViewHolder>(ShowsComparator()) {

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bindShow(item, onItemSelected) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            ShowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}