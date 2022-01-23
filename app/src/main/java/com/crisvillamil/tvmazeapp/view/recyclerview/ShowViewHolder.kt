package com.crisvillamil.tvmazeapp.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.ShowItemBinding
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.view.bindOrHide
import com.crisvillamil.tvmazeapp.view.loadImageFromURL


class ShowViewHolder(private val showItemBinding: ShowItemBinding) :
    RecyclerView.ViewHolder(showItemBinding.root) {

    fun bindShow(show: Show, onItemSelected: OnItemSelected<Show>) {
        showItemBinding.showTitle.bindOrHide(show.name)
        showItemBinding.genres.bindOrHide(show.genres.orEmpty().toString())
        showItemBinding.language.bindOrHide(show.language)
        showItemBinding.imageShow.loadImageFromURL(show.image?.original)
        showItemBinding.root.setOnClickListener {
            onItemSelected.onItemSelected(show, showItemBinding.imageShow)
        }
    }
}