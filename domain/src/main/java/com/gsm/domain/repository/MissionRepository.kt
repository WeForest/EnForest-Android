package com.gsm.domain.repository

import com.gsm.domain.entity.mission.*
import com.gsm.domain.entity.mission.request.AddMission

interface MissionRepository {


    suspend fun addMission(request: AddMission): AddMissionEntity?
    suspend fun deleteMission(number: Int): DeleteMissionEntity
    suspend fun getMission(number: Int): GetMissionEntity
    suspend fun getMissionTypePage(type:String,page:Int):List<GetMissionTypePageEntity>
    suspend fun patchMissionClear(header: String, number: Int): PathMissionClearEntity
    suspend fun patchMissionFail(header: String, number: Int): PachMissionFailEntity


}