package com.gsm.presentation.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gsm.data.entity.group.response.SearchGroupResponse
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.data.network.service.GroupService
import com.gsm.domain.entity.group.response.search.SearchGroupEntity
import com.gsm.domain.usecase.group.SearchGroupUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GroupPagingDataSource @Inject constructor(
    private val service: GroupService,
    private val k: String?,


    ) : PagingSource<Int, SearchGroupResponseItem>() {
    companion object {
        const val TAG = "GroupPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchGroupResponseItem> {
        return try {
            val page = params.key ?: 1
            val response =
                service.searchGroups(page, k)

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


    override fun getRefreshKey(state: PagingState<Int, SearchGroupResponseItem>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}