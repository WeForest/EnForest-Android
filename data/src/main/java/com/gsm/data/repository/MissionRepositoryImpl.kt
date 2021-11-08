package com.gsm.data.repository

import com.gsm.data.datasource.mission.MissionDataSourceImpl
import com.gsm.data.entity.mission.response.AddMissionResponse
import com.gsm.data.mapper.mission.toData
import com.gsm.data.mapper.mission.toDomain
import com.gsm.data.mapper.mission.toRequest
import com.gsm.domain.entity.*
import com.gsm.domain.entity.request.AddMission
import com.gsm.domain.repository.MissionRepository
import retrofit2.Response
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val dataSource: MissionDataSourceImpl
) : MissionRepository {

    // domain -> data toData
    override suspend fun addMission(request: AddMission): AddMissionEntity? =
        dataSource.addMission(request.toRequest()).body()?.toDomain()

//
//    override suspend fun deleteMission(number: Int): Response<DeleteMissionEntity> {
//        return dataSource.deleteMission()
//    }
//
//    override suspend fun getMission(number: Int): Response<GetMissionEntity> {
//        return dataSource.getMission(number)
//    }
//
//    override suspend fun getMissionType(type: String): Response<GetMissionTypeEntity> {
//        return dataSource.getMissionType(type)
//    }
//
//    override suspend fun patchMissionClear(
//        header: String,
//        number: Int
//    ): Response<PathMissionClearEntity> =
//        dataSource.patchMissionClear(header, number) {
//
//        }
//
//
//    override suspend fun patchMissionFail(
//        header: String,
//        number: Int
//    ): Response<PachMissionFailEntity> {
//        return dataSource.patchMissionFail(header, number)
//    }
}