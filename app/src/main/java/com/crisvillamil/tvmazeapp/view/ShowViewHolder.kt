package com.crisvillamil.tvmazeapp.view

import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.ShowItemBinding
import com.crisvillamil.tvmazeapp.model.Show
import com.squareup.picasso.Picasso


class ShowViewHolder(private val showItemBinding: ShowItemBinding) :
    RecyclerView.ViewHolder(showItemBinding.root) {

    fun bindShow(show: Show, onItemSelected: OnItemSelected) {
        showItemBinding.showTitle.text = show.name
        if (show.genres.isNotEmpty()) showItemBinding.genres.text = show.genres.toString()
        showItemBinding.language.text = show.language
        Picasso
            .get()
            .load(show.image.original)
            .into(showItemBinding.imageShow)
        showItemBinding.root.setOnClickListener {
            onItemSelected.onItemSelected(show, showItemBinding.imageShow)
        }
    }
}