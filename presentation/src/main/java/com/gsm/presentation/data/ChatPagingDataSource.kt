package com.gsm.presentation.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gsm.data.network.service.GroupService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChatPagingDataSource @Inject constructor(
    private val service: GroupService,
    private val k: String,


    ) : PagingSource<Int, Unit>() {
    companion object {
        const val TAG = "PostPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Unit> {
        return try {
            val page = params.key ?: 1
            val response = service.searchChat(page, k)


            val responseData = mutableListOf<Unit>()
//            val data = response.body()?.posts ?: emptyList()

//            responseData.addAll(data)


            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = responseData,
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


    override fun getRefreshKey(state: PagingState<Int, Unit>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}
