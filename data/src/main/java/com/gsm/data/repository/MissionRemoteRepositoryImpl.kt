package com.gsm.data.repository

import com.gsm.data.datasource.mission.remote.MissionRemoteDataSourceImpl
import com.gsm.data.mapper.mission.remote.toDomain
import com.gsm.data.mapper.mission.toDomain
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.repository.MissionRemoteRepository
import javax.inject.Inject

class MissionRemoteRepositoryImpl @Inject constructor(
    private val dataSourceImpl: MissionRemoteDataSourceImpl,
) :
    MissionRemoteRepository {
    override suspend fun insertMission(mission: GetMissionEntity) {
        return dataSourceImpl.insertMission(mission = mission.toDomain())

    }

    override suspend fun deleteMission(mission: GetMissionEntity) {
        return dataSourceImpl.deleteMission(mission = mission.toDomain())

    }

    override suspend fun getMission(type: String): List<GetMissionEntity> {
        return dataSourceImpl.getMission(type).map { it.toDomain() }
    }


}