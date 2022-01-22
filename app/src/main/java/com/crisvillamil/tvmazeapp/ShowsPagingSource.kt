package com.crisvillamil.tvmazeapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.network.TVMazeAPI

class ShowsPagingSource(private val TVMazeAPI: TVMazeAPI) : PagingSource<Int, Show>() {

    override fun getRefreshKey(state: PagingState<Int, Show>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        val pageNumber = params.key ?: 1
        return try {
            val response = TVMazeAPI.getAllShows(pageNumber)
            val pagedResponse = response.body()
            LoadResult.Page(
                data = pagedResponse!!,
                prevKey = null,
                nextKey = pageNumber + 1
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            LoadResult.Error(exception)
        }


    }
}