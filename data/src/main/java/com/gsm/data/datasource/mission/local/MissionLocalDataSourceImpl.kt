package com.gsm.data.datasource.mission.local

import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import com.gsm.data.network.service.MissionService
import javax.inject.Inject

class MissionLocalDataSourceImpl @Inject constructor(
    override val service: MissionService

) : BaseDataSource<MissionService>(), MissionLocalDataSource {
    override suspend fun addMission(request: AddMissionRequest): AddMissionResponse {
        return service.addMission(request)
    }

    override suspend fun deleteMission(number: Int): DeleteMissioNResponse {
        return service.deleteMission(number)
    }

    override suspend fun getMission(number: Int): GetMissionResponse {
        return service.getMission(number)
    }

    override suspend fun getMissionTypePage(type: String, page: Int): List<GetMissionTypePageResponse> {
        return service.getMissionTypePage(type, page)
    }


    override suspend fun patchMissionClear(
        header: String,
        number: Int
    ): PathMissionClearResponse {
        return service.patchMissionClear(header, number)
    }

    override suspend fun patchMissionFail(
        header: String,
        number: Int
    ): PathMissionFailResponse {
        return service.patchMissionFail(header, number)
    }
}