package com.kareemdev.dicodingstory.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.data.remote.ApiService
import com.kareemdev.dicodingstory.utils.Constants.INITIAL_PAGE_INDEX
import com.kareemdev.dicodingstory.utils.Constants.token
import java.lang.Exception

class StoryPagingSource(private val apiService: ApiService): PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListStories(
                token,
                page,
                params.loadSize)
            val data = responseData.listStory
            LoadResult.Page(
                data = data,
                prevKey = if(page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if(data.isNullOrEmpty()) null else page + 1,
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

}