package com.gsm.domain.repository

import com.gsm.domain.entity.*
import com.gsm.domain.entity.request.AddMission
import retrofit2.Response

interface MissionRepository {


    suspend fun addMission(request: AddMission): AddMissionEntity?
    suspend fun deleteMission(number: Int): DeleteMissionEntity
    suspend fun getMission(number: Int): GetMissionEntity
    suspend fun getMissionType(type: String): GetMissionTypeEntity
    suspend fun patchMissionClear(header: String, number: Int): PathMissionClearEntity
    suspend fun patchMissionFail(header: String, number: Int): PachMissionFailEntity

}