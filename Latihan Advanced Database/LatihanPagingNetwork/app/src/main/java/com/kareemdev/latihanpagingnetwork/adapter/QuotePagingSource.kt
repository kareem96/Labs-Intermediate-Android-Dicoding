package com.kareemdev.latihanpagingnetwork.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kareemdev.latihanpagingnetwork.network.ApiService
import com.kareemdev.latihanpagingnetwork.network.QuoteResponseItem
import java.lang.Exception

class QuotePagingSource(private val apiService: ApiService) : PagingSource<Int, QuoteResponseItem>() {
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, QuoteResponseItem>): Int? {
        return state.anchorPosition?.let { anc }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getQuote(position, params.loadSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if(responseData.isNullOrEmpty()) null else position + 1
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

}