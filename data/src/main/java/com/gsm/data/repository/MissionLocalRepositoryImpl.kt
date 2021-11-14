package com.gsm.data.repository

import com.gsm.data.datasource.mission.local.MissionLocalDataSourceImpl
import com.gsm.data.mapper.mission.toDomain
import com.gsm.data.mapper.mission.toRequest
import com.gsm.domain.entity.mission.*
import com.gsm.domain.entity.mission.request.AddMission
import com.gsm.domain.repository.MissionRepository
import javax.inject.Inject

class MissionLocalRepositoryImpl @Inject constructor(
    private val dataSource: MissionLocalDataSourceImpl
) : MissionRepository {

    // domain -> data toData
    override suspend fun addMission(request: AddMission): AddMissionEntity =
        dataSource.addMission(request.toRequest()).toDomain()


    override suspend fun deleteMission(number: Int): DeleteMissionEntity {
        return dataSource.deleteMission(number).toDomain()
    }

    override suspend fun getMission(number: Int): GetMissionEntity {
        return dataSource.getMission(number).toDomain()
    }

    override suspend fun getMissionTypePage(
        type: String,
        page: Int
    ): List<GetMissionTypePageEntity> {
        return dataSource.getMissionTypePage(type, page).map { it.toDomain() }
    }


    override suspend fun patchMissionClear(
        header: String,
        number: Int
    ): PathMissionClearEntity =
        dataSource.patchMissionClear(header, number).toDomain()


    override suspend fun patchMissionFail(
        header: String,
        number: Int
    ): PachMissionFailEntity {
        return dataSource.patchMissionFail(header, number).toDomain()
    }
}