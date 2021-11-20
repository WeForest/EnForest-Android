package com.gsm.presentation.viewmodel.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.data.network.service.GroupService
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.GroupData
import com.gsm.domain.usecase.group.*
import com.gsm.presentation.data.ChatPagingDataSource
import com.gsm.presentation.data.GroupPagingDataSource
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val secessionGroupUseCase: SecessionGroupUseCase,
    private val searchGroupUseCase: SearchGroupUseCase,
    private val service: GroupService
) : ViewModel() {
    val TAG = "GROUP"
    private val _createGroupValue = MutableLiveData<GroupData?>()
    val createGroupValue: LiveData<GroupData?>
        get() = _createGroupValue

    private val _success = MutableLiveData<Event<Boolean?>>()
    val success: LiveData<Event<Boolean?>>
        get() = _success

    suspend fun createGroup(token: String, name: String, description: String, tags: String) =
        viewModelScope.launch {
            Log.d(TAG, "GroupViewModel - createGroup() :${token}")

            try {
                createGroupUseCase.buildUseCaseObservable(
                    CreateGroupUseCase.Params(
                        token,
                        CreateGroup(name, description, tags)
                    )
                ).let {
                    if (it.success == true) {
                        Log.d(TAG, "createGroup: 성공 ${it.group}")
                        _success.value = Event(it.success)

                        _createGroupValue.value = it.group
                    } else {
                        _success.value = Event(it.success)
                        Log.e(TAG, "createGroup: 실패 ${it.group} ${it}")

                    }
                }
            } catch (e: Exception) {
                _success.value = Event(false)

                Log.d(TAG, "createGroup: $e")
            }
        }

    private val _query = MutableLiveData<Event<String>>()

    fun getQuery(query: String): Flow<PagingData<SearchGroupResponseItem>> {

        return Pager(

            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            GroupPagingDataSource(
                service,
                query,
            )

        }.flow
            .cachedIn(viewModelScope)
    }


    val chatData = Pager(

        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        ChatPagingDataSource(
            service,
            _query.value.toString(),
        )

    }.flow
        .cachedIn(viewModelScope)


}