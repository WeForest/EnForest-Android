package com.gsm.data.datasource.mission

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import retrofit2.Response

interface MissionDataSource {
    suspend fun addMission(request: AddMissionRequest): AddMissionResponse
    suspend fun deleteMission(number: Int): DeleteMissioNResponse
    suspend fun getMission(number: Int): GetMissionResponse
    suspend fun getMissionTypePage(type: String, number: Int): List<GetMissionTypePageResponse>
    suspend fun patchMissionClear(header: String, number: Int): PathMissionClearResponse
    suspend fun patchMissionFail(header: String, number: Int): PathMissionFailResponse
}