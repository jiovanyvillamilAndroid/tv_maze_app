package com.crisvillamil.tvmazeapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.crisvillamil.tvmazeapp.R
import com.crisvillamil.tvmazeapp.databinding.ActivityEpisodeDetailBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.view.recyclerview.SeasonViewHolder.Companion.EPISODE_DATA_KEY
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class EpisodeDetailActivity : AppCompatActivity() {
    private lateinit var episodeDetailBinding: ActivityEpisodeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        val episode = getEpisodeData()
        bindImage(episode?.image?.original)
        bindTexts(episode)
    }

    private fun bindTexts(episodeResponse: EpisodeResponse?) {
        episodeResponse?.let { episode ->
            bindEpisodeName(episode)
            episodeDetailBinding.seasonTitle.bindOrHide("Season ${episode.season}")
            if (episode.summary != null)
                episodeDetailBinding.summary.text =
                    HtmlCompat.fromHtml(episode.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun bindEpisodeName(episode: EpisodeResponse) {
        val episodeNumber = episode.number
        if (episodeNumber != null) {
            episodeDetailBinding.episodeTitle.bindOrHide("${episode.number}. ${episode.name}")
        } else {
            episodeDetailBinding.episodeTitle.bindOrHide(episode.name)
        }
    }

    private fun bindImage(imageURL: String?) {
        Picasso
            .get()
            .load(imageURL)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(episodeDetailBinding.episodeImage, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })
    }

    private fun initViewBinding() {
        episodeDetailBinding = ActivityEpisodeDetailBinding.inflate(layoutInflater)
        setContentView(episodeDetailBinding.root)
    }

    private fun getEpisodeData(): EpisodeResponse? {
        return try {
            intent.getParcelableExtra(EPISODE_DATA_KEY)!!
        } catch (exception: Exception) {
            exception.printStackTrace()
            supportStartPostponedEnterTransition()
            Toast.makeText(this, "Can't get info for that episode", Toast.LENGTH_LONG).show()
            //TODO: Hide views and show Error View
            null
        }
    }
}