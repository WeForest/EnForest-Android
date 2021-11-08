package com.gsm.data.datasource.mission

import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import com.gsm.data.network.service.MissionService
import retrofit2.Response
import javax.inject.Inject

class MissionDataSourceImpl @Inject constructor(
    override val service: MissionService

) : BaseDataSource<MissionService>(), MissionDataSource {
    override suspend fun addMission(request: AddMissionRequest): Response<AddMissionResponse> {
        return service.addMission(request)
    }

    override suspend fun deleteMission(number: Int): Response<DeleteMissioNResponse> {
        return service.deleteMission(number)
    }

    override suspend fun getMission(number: Int): Response<GetMissionResponse> {
        return service.getMission(number)
    }

    override suspend fun getMissionType(type: String): Response<GetMissionTypeResponse> {
        return service.getMissionType(type)
    }

    override suspend fun patchMissionClear(
        header: String,
        number: Int
    ): Response<PathMissionClearResponse> {
        return service.patchMissionClear(header, number)
    }

    override suspend fun patchMissionFail(
        header: String,
        number: Int
    ): Response<PathMissionFailResponse> {
        return service.patchMissionFail(header, number)
    }
}