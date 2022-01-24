package com.crisvillamil.tvmazeapp.view

import android.app.AlertDialog
import android.os.Bundle
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
            episodeDetailBinding.seasonTitle.bindOrHide(
                resources.getString(
                    R.string.seasons_name,
                    episode.season
                )
            )
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
            showErrorDialog()
            null
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_text)
            .setMessage(R.string.fail_detail_episode)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                this.finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}