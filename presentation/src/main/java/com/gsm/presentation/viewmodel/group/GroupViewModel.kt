package com.gsm.presentation.viewmodel.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.CreateGroupEntity
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

    private val _createGroupValue = MutableLiveData<CreateGroupEntity>()
     val createGroupValue : LiveData<CreateGroupEntity>
     get()=_createGroupValue
    suspend fun createGroup(token: String, name: String, description: String, tags: String) =
        viewModelScope.launch {
            try {
                createGroupUseCase.buildUseCaseObservable(
                    CreateGroupUseCase.Params(
                        "",
                        CreateGroup(name, description, tags)
                    )
                ).let {
                    _createGroupValue.value=it
                }
            } catch (e: Exception) {

            }
        }
}