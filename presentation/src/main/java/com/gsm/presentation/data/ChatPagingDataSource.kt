package com.gsm.presentation.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gsm.data.entity.group.response.SearchChatResponseItem
import com.gsm.data.network.service.GroupService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ChatPagingDataSource @Inject constructor(
    private val service: GroupService,
    private val k: String?,


    ) : PagingSource<Int, SearchChatResponseItem>() {
    companion object {
        const val TAG = "GroupPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchChatResponseItem> {
        return try {
            val page = params.key ?: 1
            val response =
                service.searchChat(page, k.toString())

            Log.d(TAG, "load: data  ${response}")

            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = null,
            )


        } catch (e: Exception) {
            Log.d(TAG, "error: $e")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: $e")
            return LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d(TAG, "IOException: $e")
            return LoadResult.Error(e)
        }


    }


    override fun getRefreshKey(state: PagingState<Int, SearchChatResponseItem>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}
