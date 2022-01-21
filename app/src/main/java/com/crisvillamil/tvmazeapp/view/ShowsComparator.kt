package com.crisvillamil.tvmazeapp.view

import androidx.recyclerview.widget.DiffUtil
import com.crisvillamil.tvmazeapp.model.Show

class ShowsComparator : DiffUtil.ItemCallback<Show>() {
    override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
        return oldItem.name == newItem.name && oldItem.genres == newItem.genres && oldItem.image == newItem.image
    }
}