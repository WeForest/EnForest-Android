package com.gsm.presentation.viewmodel.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.entity.group.response.GroupData
import com.gsm.domain.usecase.group.CreateGroupUseCase
import com.gsm.domain.usecase.group.DeleteGroupUseCase
import com.gsm.domain.usecase.group.JoinGroupUseCase
import com.gsm.domain.usecase.group.SecessionGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val secessionGroupUseCase: SecessionGroupUseCase
) : ViewModel() {
val TAG="GROUP"
    private val _createGroupValue = MutableLiveData<GroupData?>()
     val createGroupValue : LiveData<GroupData?>
     get()=_createGroupValue
    suspend fun createGroup(token: String, name: String, description: String, tags: String) =
        viewModelScope.launch {
            Log.d(TAG,"GroupViewModel - createGroup() :${token}")

            try {
                createGroupUseCase.buildUseCaseObservable(
                    CreateGroupUseCase.Params(
                        token,
                        CreateGroup(name, description, tags)
                    )
                ).let {
                    if (it.success == true) {
                        Log.d(TAG, "createGroup: 성공 ${it.group}")
                        _createGroupValue.value = it.group
                    }else{
                        Log.e(TAG, "createGroup: 실패 ${it.group} ${it}")

                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "createGroup: $e")
            }
        }
}