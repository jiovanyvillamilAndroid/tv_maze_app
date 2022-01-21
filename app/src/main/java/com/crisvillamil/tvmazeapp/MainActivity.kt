package com.crisvillamil.tvmazeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.HORIZONTAL
import com.crisvillamil.tvmazeapp.databinding.ActivityMainBinding
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.view.MainActivityViewModel
import com.crisvillamil.tvmazeapp.view.ShowsAdapter
import kotlinx.coroutines.flow.collectLatest
import androidx.core.app.ActivityOptionsCompat

import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {
    private val staggeredGridRowsCount = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        val showsAdapter = ShowsAdapter(object : OnItemSelected {

            override fun onItemSelected(showItem: Show, sharedElementTransition: View) {
                val intent = Intent(this@MainActivity, ShowDetailActivity::class.java)
                intent.putExtra("imageURL", showItem.image.original)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    sharedElementTransition,
                    "imageShow"
                )
                startActivity(intent, options.toBundle())
            }

        })
        mainActivityBinding.seriesRecyclerView.apply {
            adapter = showsAdapter
            layoutManager = StaggeredGridLayoutManager(staggeredGridRowsCount, HORIZONTAL)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.showsFlow.collectLatest { pagingData ->
                showsAdapter.submitData(pagingData)
            }
        }


    }
}