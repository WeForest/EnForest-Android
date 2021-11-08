package com.gsm.data.datasource.mission

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import retrofit2.Response

interface MissionDataSource {
    suspend fun addMission(request: AddMissionRequest): Response<AddMissionResponse>
    suspend fun deleteMission(number: Int): Response<DeleteMissioNResponse>
    suspend fun getMission(number: Int): Response<GetMissionResponse>
    suspend fun getMissionType(type: String): Response<GetMissionTypeResponse>
    suspend fun patchMissionClear(header: String, number: Int): Response<PathMissionClearResponse>
    suspend fun patchMissionFail(header: String, number: Int): Response<PathMissionFailResponse>


}