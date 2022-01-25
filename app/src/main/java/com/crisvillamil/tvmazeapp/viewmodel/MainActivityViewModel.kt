package com.crisvillamil.tvmazeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crisvillamil.tvmazeapp.view.recyclerview.ShowsPagingSource
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.network.TVMazeAPIProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val mutableShowsList = MutableLiveData<PagingData<Show>>()
    val showListLiveData: LiveData<PagingData<Show>> = mutableShowsList

    private val mutableShowsListFind = MutableLiveData<List<Show>>()
    val showListFindLiveData: LiveData<List<Show>> = mutableShowsListFind

    fun fetchShows() {
        viewModelScope.launch {
            showsFlow.collectLatest { pagingData ->
                mutableShowsList.value = pagingData
            }
        }
    }

    fun searchShows(searchText: String) {
        viewModelScope.launch {
            mutableShowsListFind.value =
                TVMazeAPIProvider.getTVMazeApi().searchShows(searchText).body()?.map {
                    it.show
                }
        }
    }

    private val showsFlow: Flow<PagingData<Show>> =
        Pager(config = PagingConfig(pageSize = 250, prefetchDistance = 1),
            pagingSourceFactory = { ShowsPagingSource(TVMazeAPIProvider.getTVMazeApi()) }).flow.cachedIn(
            viewModelScope
        )

}