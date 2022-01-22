package com.crisvillamil.tvmazeapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.HORIZONTAL
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.databinding.ActivityMainBinding
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.view.recyclerview.ShowsAdapter
import com.crisvillamil.tvmazeapp.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest


class MainActivity : AppCompatActivity() {

    companion object {
        const val SHOW_ITEM_KEY = "ShowItemKey"
        private const val SHARE_ELEMENT_TRANSITION_NAME = "imageShow"
    }

    private val staggeredGridRowsCount = 2
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var showsAdapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        initShowsAdapter()
        initRecyclerView()
        fetchShows()
    }

    private fun initShowsAdapter() {
        showsAdapter = ShowsAdapter(object : OnItemSelected {
            override fun onItemSelected(showItem: Show, sharedElementTransition: View) {
                navigateToShowDetail(showItem, sharedElementTransition)
            }
        })
    }

    private fun navigateToShowDetail(showItem: Show, sharedElementTransition: View) {
        val intent = Intent(this@MainActivity, ShowDetailActivity::class.java)
        intent.putExtra(SHOW_ITEM_KEY, showItem)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity,
            sharedElementTransition,
            SHARE_ELEMENT_TRANSITION_NAME
        )
        startActivity(intent, options.toBundle())
    }

    private fun initRecyclerView() {
        mainActivityBinding.seriesRecyclerView.apply {
            adapter = showsAdapter
            layoutManager = StaggeredGridLayoutManager(staggeredGridRowsCount, HORIZONTAL)
        }
    }

    private fun fetchShows() {
        lifecycleScope.launchWhenCreated {
            viewModel.showsFlow.collectLatest { pagingData ->
                showsAdapter.submitData(pagingData)
            }
        }
    }
}