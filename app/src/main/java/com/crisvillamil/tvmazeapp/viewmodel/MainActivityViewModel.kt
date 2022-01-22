package com.crisvillamil.tvmazeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crisvillamil.tvmazeapp.ShowsPagingSource
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.network.TVMazeAPIProvider
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel : ViewModel() {

    val showsFlow: Flow<PagingData<Show>> =
        Pager(config = PagingConfig(pageSize = 250, prefetchDistance = 1),
            pagingSourceFactory = { ShowsPagingSource(TVMazeAPIProvider.getTVMazeApi()) }).flow.cachedIn(
            viewModelScope
        )

}