package com.gsm.domain.usecase.mission.remote

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.repository.MissionRemoteRepository
import javax.inject.Inject

class InsertMissionUsdCase @Inject constructor(private val missionRemoteRepository: MissionRemoteRepository) {


    data

    class Params(val getMissionEntity: GetMissionEntity)

    suspend fun buildUseCaseObservable(params: Params) {
        return missionRemoteRepository.insertMission(params.getMissionEntity)
    }

}