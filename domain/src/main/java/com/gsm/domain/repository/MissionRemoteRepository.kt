package com.gsm.domain.repository

import com.gsm.domain.entity.mission.GetMissionEntity

interface MissionRemoteRepository {

    suspend fun insertMission(mission: GetMissionEntity)
    suspend fun deleteMission(mission: GetMissionEntity)


    suspend fun getMission(type:String): List<GetMissionEntity>


}