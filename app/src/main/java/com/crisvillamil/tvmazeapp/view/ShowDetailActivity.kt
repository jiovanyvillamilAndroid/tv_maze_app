package com.crisvillamil.tvmazeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import java.lang.Exception

class ShowDetailActivity : AppCompatActivity() {
    private lateinit var showDetailBinding: ActivityShowDetailBinding
    private lateinit var viewModel: ShowDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViewBinding()
        supportPostponeEnterTransition()
        val showData = getShowData()
        showData?.let {
            bindShowInfo(showData)
            initSeasonsFetch(showData.id)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ShowDetailViewModel::class.java)
    }

    private fun initSeasonsFetch(showId: Int) {

        val jobs = arrayListOf<Job>()
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            //ShowLoading
            val seasons = viewModel.getSeasons(showId)
            if (seasons != null) {
                val subItems = seasons.map {
                    async { it.id to viewModel.getEpisodes(it.id) }
                }.awaitAll()
                val itemsMap = subItems.map {
                    it.first to it.second
                }.toMap()
                showDetailBinding.seasonsRecyclerView.adapter =
                    SeasonsAdapter(seasons, itemsMap)
            } else {
                Toast.makeText(
                    this@ShowDetailActivity,
                    "Can't retrieve seasons info",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getShowData(): Show? {
        return try {
            intent.getParcelableExtra(SHOW_ITEM_KEY)!!
        } catch (exception: Exception) {
            exception.printStackTrace()
            supportStartPostponedEnterTransition()
            Toast.makeText(this, "Can't get info for that show", Toast.LENGTH_LONG).show()
            //TODO: Hide views and show Error View
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
        //.placeholder(R.drawable.ic_image_placeholder)
        .error(R.drawable.ic_image_error)
        .into(this)
}