package com.crisvillamil.tvmazeapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crisvillamil.tvmazeapp.R
import com.crisvillamil.tvmazeapp.databinding.ActivityShowDetailBinding
import com.crisvillamil.tvmazeapp.model.EpisodeResponse
import com.crisvillamil.tvmazeapp.model.SeasonResponse
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.view.MainActivity.Companion.SHOW_ITEM_KEY
import com.crisvillamil.tvmazeapp.view.recyclerview.SeasonsAdapter
import com.crisvillamil.tvmazeapp.viewmodel.ShowDetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class ShowDetailActivity : AppCompatActivity() {
    private lateinit var showDetailBinding: ActivityShowDetailBinding
    private lateinit var viewModel: ShowDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViewBinding()
        supportPostponeEnterTransition()
        bindData()
        initSeasonsObserver()
    }

    private fun bindData() {
        val showData = getShowData()
        showData?.let {
            bindShowInfo(showData)
            viewModel.viewModelScope.launch {
                viewModel.getSeasons(showData.id)
            }
        }
    }

    private fun initSeasonsObserver() {
        viewModel.itemsMapLiveData.observe(this, { data ->
            showDetailBinding.loader.visibility = View.GONE
            if (!data.first.isNullOrEmpty()) {
                onSeasonsSuccess(data)
            } else {
                onSeasonFailed()
            }
        })
    }

    private fun onSeasonFailed() {
        Toast.makeText(
            this@ShowDetailActivity,
            resources.getString(R.string.seasons_retrieve_error),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onSeasonsSuccess(data: Pair<List<SeasonResponse>, Map<Int, List<EpisodeResponse>?>>) {
        showDetailBinding.seasonsRecyclerView.adapter =
            SeasonsAdapter(data.first, data.second)
        Toast.makeText(
            this@ShowDetailActivity,
            resources.getString(R.string.seasons_loaded),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_text)
            .setMessage(R.string.fail_detail_show)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                this.finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ShowDetailViewModel::class.java)
    }

    private fun getShowData(): Show? {
        return try {
            intent.getParcelableExtra(SHOW_ITEM_KEY)!!
        } catch (exception: Exception) {
            exception.printStackTrace()
            supportStartPostponedEnterTransition()
            showErrorDialog()
            null
        }
    }

    private fun initViewBinding() {
        showDetailBinding = ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(showDetailBinding.root)
    }

    private fun bindShowInfo(show: Show?) {
        show?.let {
            bindText(it)
            bindImage(it)
        }
    }

    private fun bindText(show: Show) {
        showDetailBinding.showName.bindOrHide(show.name)
        showDetailBinding.genres.bindOrHide(show.genres.toString())
        showDetailBinding.premierDate.bindOrHide(show.premiered)
        showDetailBinding.finishedDate.bindOrHide(show.ended)
        if (show.summary != null)
            showDetailBinding.summary.text =
                HtmlCompat.fromHtml(show.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun bindImage(show: Show) {
        val imageURL = show.image?.original
        Picasso
            .get()
            .load(imageURL)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(showDetailBinding.imageShow, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })
    }

    override fun onDestroy() {
        supportFinishAfterTransition()
        super.onDestroy()
    }
}

fun TextView.bindOrHide(value: String?) {
    if (value == null) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
        this.text = value
    }
}

fun ImageView.loadImageFromURL(url: String?) {
    Picasso
        .get()
        .load(url)
        .error(R.drawable.ic_image_error)
        .into(this)
}
