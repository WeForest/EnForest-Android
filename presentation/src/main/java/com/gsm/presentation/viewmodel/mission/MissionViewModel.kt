package com.gsm.presentation.viewmodel.mission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.entity.mission.GetMissionTypePageEntity
import com.gsm.domain.entity.mission.request.AddMission
import com.gsm.domain.usecase.mission.AddMissionUseCase
import com.gsm.domain.usecase.mission.GetMissionTypePageUseCase
import com.gsm.domain.usecase.mission.GetMissionUseCase
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor
    (
    private val addMissionUseCase: AddMissionUseCase,
    private val getMissionUseCase: GetMissionUseCase,
    private val getMissionTypePageUseCase: GetMissionTypePageUseCase
) : ViewModel() {
    private val TAG = "mission"

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    private val _exp = MutableLiveData<Int>()
    val exp: LiveData<Int> get() = _exp
    private val _success = MutableLiveData<Event<Boolean>>()
    private val _missionData = MutableLiveData<GetMissionEntity>()
    val missionData: LiveData<GetMissionEntity> get() = _missionData

    private val _missionPageData = MutableLiveData<Event<List<GetMissionTypePageEntity>>>()
    val missionPageData: LiveData<Event<List<GetMissionTypePageEntity>>> get() = _missionPageData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        _errorMessage.value=""
    }

    val success: LiveData<Event<Boolean>> get() = _success
    suspend fun addMission(level:String,title: String, content: String, expiredAt: Int, type: String) =
        viewModelScope.launch {
            try {
                Log.d(TAG, "addMission: content : ${content}")
                addMissionUseCase.buildUseCaseObservable(
                    AddMissionUseCase.Params(
                        AddMission(
                            level,
                            0,
                            title,
                            content,
                            expiredAt,
                            type
                        )
                    )
                ).let {
                    Log.d("mission", "addMission: 성공 ")
                    Log.d(TAG, "addMission: ${it}")
                    _success.value = Event(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "addMission: ${e}")
                _success.value = Event(false)
            }
        }

    suspend fun getMission(number: Int) = viewModelScope.launch {

        try {
            getMissionUseCase.buildUseCaseObservable(GetMissionUseCase.Params(number)).let {
                _missionData.value = it
                Log.d(TAG, "getMission: ${it?.title}")
            }


        } catch (e: Exception) {

        }
    }

    suspend fun getMissionType(type: String, page: Int) = viewModelScope.launch {
        try {
            Log.d(TAG, "getMissionType: type : ${type} page : ${page}")
            getMissionTypePageUseCase.buildUseCaseObservable(
                GetMissionTypePageUseCase.Params(
                    type,
                    page
                )
            ).let {
                Log.d(TAG, "MissionViewModel - getMissionType : $it")

                if (it?.isNotEmpty() == true) {
                    _missionPageData.value = Event(it)
                    _errorMessage.value = ""

                } else {
                    Log.d(TAG, "getMissionType:에러 ")
                    _errorMessage.value = "에러"
                }


            }
        } catch (e: Exception) {
            Log.e(TAG, "getMissionType: ${e.message}")

            _errorMessage.value = e.message

        }
    }
}
