package com.gsm.presentation.viewmodel.mission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.usecase.mission.remote.GetMissionUseCase
import com.gsm.domain.usecase.mission.remote.InsertMissionUsdCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionRemoteViewModel @Inject constructor(
    private val getMissionUseCase: GetMissionUseCase,
    private val insertMissionUsdCase: InsertMissionUsdCase,
) : ViewModel() {

    suspend fun insertMission(getMissionEntity: GetMissionEntity) = viewModelScope.launch {
        Log.d(TAG, "insertMission: ${getMissionEntity} ")
        insertMissionUsdCase.buildUseCaseObservable(InsertMissionUsdCase.Params(getMissionEntity))
    }


    private val _getMission = MutableLiveData<List<GetMissionEntity>?>()
    val getMission: LiveData<List<GetMissionEntity>?> get() = _getMission


    fun getMission(type: String, level: String) = viewModelScope.launch {
        Log.d(TAG, "MissionRemoteViewModel - getMission() called ${type},${level}")
        getMissionUseCase.getMission(type, level).let {
            Log.d(TAG, "getMission: ${it}")
            _getMission.value = it
        }
    }

    val TAG = "REMOTE"

}