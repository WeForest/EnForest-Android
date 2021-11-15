package com.gsm.data.datasource.mission.remote

import com.gsm.data.entity.mission.response.GetMissionResponse

interface MissionRemoteDataSource {
    suspend fun insertMission(mission: GetMissionResponse)
    suspend fun deleteMission(mission: GetMissionResponse)


    fun getMission(type:String,level:String): List<GetMissionResponse>


}