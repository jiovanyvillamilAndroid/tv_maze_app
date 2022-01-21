package com.crisvillamil.tvmazeapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crisvillamil.tvmazeapp.ShowsPagingSource
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.network.ShowsApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivityViewModel : ViewModel() {


    val showsFlow: Flow<PagingData<Show>> =
        Pager(config = PagingConfig(pageSize = 250, prefetchDistance = 1),
            pagingSourceFactory = { ShowsPagingSource(getShowsApi()) }).flow.cachedIn(viewModelScope)


    private fun getShowsApi(): ShowsApi {
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://api.tvmaze.com/")
            addConverterFactory(MoshiConverterFactory.create())
        }.build()
        return retrofit.create(ShowsApi::class.java)
    }
}