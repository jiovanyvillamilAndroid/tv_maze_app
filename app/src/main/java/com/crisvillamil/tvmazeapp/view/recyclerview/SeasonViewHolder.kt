package com.crisvillamil.tvmazeapp.view.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.R
import com.crisvillamil.tvmazeapp.databinding.SeasonItemBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.view.EpisodeDetailActivity
import com.crisvillamil.tvmazeapp.view.bindOrHide

class SeasonViewHolder(private val seasonItemBinding: SeasonItemBinding) :
    RecyclerView.ViewHolder(seasonItemBinding.root) {

    companion object {
        const val EPISODE_DATA_KEY = "episodeDataKey"
        private const val EPISODE_SHARE_ELEMENT_TRANSITION_NAME = "episodeImage"
    }

    fun bindSeason(season: SeasonResponse, episodes: List<EpisodeResponse>?) {

        seasonItemBinding.seasonsId.bindOrHide(
            seasonItemBinding.root.context.resources.getString(
                R.string.seasons_name,
                season.number
            )
        )
        episodes?.let {
            seasonItemBinding.episodesRecyclerView.adapter =
                EpisodesAdapter(episodes, object : OnItemSelected<EpisodeResponse> {
                    override fun onItemSelected(
                        item: EpisodeResponse,
                        sharedElementTransition: View
                    ) {
                        val context = seasonItemBinding.root.context
                        val intent =
                            Intent(
                                context,
                                EpisodeDetailActivity::class.java
                            )
                        intent.putExtra(EPISODE_DATA_KEY, item)
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            sharedElementTransition,
                            EPISODE_SHARE_ELEMENT_TRANSITION_NAME
                        )
                        context.startActivity(intent, options.toBundle())
                    }

                })
        }
    }
}