package com.gsm.presentation.viewmodel.mission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.GetMissionEntity
import com.gsm.domain.entity.request.AddMission
import com.gsm.domain.usecase.mission.AddMissionUseCase
import com.gsm.domain.usecase.mission.GetMissionUseCase
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor
    (
    private val addMissionUseCase: AddMissionUseCase,
    private val getMissionUseCase: GetMissionUseCase
) : ViewModel() {
    private val TAG = "mission"

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    private val _exp = MutableLiveData<Int>()
    val exp: LiveData<Int> get() = _exp
    private val _success = MutableLiveData<Event<Boolean>>()
    private val _missionData = MutableLiveData<GetMissionEntity>()
    val missionData: LiveData<GetMissionEntity> get() = _missionData

    init {
        _success.value = Event(false)
    }


    val success: LiveData<Event<Boolean>> get() = _success
    suspend fun addMission(title: String, content: String, expiredAt: Int, type: String) =
        viewModelScope.launch {
            try {
                Log.d(TAG, "addMission: content : ${content}")
                addMissionUseCase.buildUseCaseObservable(
                    AddMissionUseCase.Params(
                        AddMission(
                            0,
                            0,
                            title,
                            content,
                            expiredAt,
                            type
                        )
                    )
                ).let {
                    Log.d("mission", "addMission: 성공 ")
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
            }


        } catch (e: Exception) {

        }

    }
}
