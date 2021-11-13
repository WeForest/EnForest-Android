package com.gsm.domain.repository

import com.gsm.domain.entity.mission.GetMissionEntity
import kotlinx.coroutines.flow.Flow

interface MissionRemoteRepository {

    suspend fun insertMission(mission: GetMissionEntity)
    suspend fun deleteMission(mission: GetMissionEntity)


     suspend fun getMission(type:String, level:String): List<GetMissionEntity>

}