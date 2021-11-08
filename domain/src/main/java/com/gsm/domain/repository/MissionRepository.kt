package com.gsm.domain.repository

import com.gsm.domain.entity.*
import com.gsm.domain.entity.request.AddMission
import retrofit2.Response

interface MissionRepository {


    suspend fun addMission(request: AddMission): AddMissionEntity?
//    suspend fun deleteMission(number: Int): Response<DeleteMissionEntity>
//    suspend fun getMission(number: Int): Response<GetMissionEntity>
//    suspend fun getMissionType(type: String): Response<GetMissionTypeEntity>
//    suspend fun patchMissionClear(header: String, number: Int): Response<PathMissionClearEntity>
//    suspend fun patchMissionFail(header: String, number: Int): Response<PachMissionFailEntity>

}