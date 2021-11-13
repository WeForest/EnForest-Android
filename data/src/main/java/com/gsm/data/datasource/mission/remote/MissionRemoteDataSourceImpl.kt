package com.gsm.data.datasource.mission.remote

import com.gsm.data.db.mission.MissionDao
import com.gsm.data.entity.mission.response.GetMissionResponse
import javax.inject.Inject

class MissionRemoteDataSourceImpl @Inject constructor(private val dao: MissionDao) :
    MissionRemoteDataSource {
    override suspend fun insertMission(mission: GetMissionResponse) {
        return dao.insertMission(mission)
    }

    override suspend fun deleteMission(mission: GetMissionResponse) {
        return dao.deleteMission(mission)
    }

    override  fun getMission(type: String, level:String): List<GetMissionResponse>{
        return dao.getMission(type,level)
    }



}