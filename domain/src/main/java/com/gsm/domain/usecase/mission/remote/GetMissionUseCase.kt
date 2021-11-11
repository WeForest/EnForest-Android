package com.gsm.domain.usecase.mission.remote

import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.repository.MissionRemoteRepository
import javax.inject.Inject

class GetMissionUseCase @Inject constructor(private val missionRemoteRepository: MissionRemoteRepository) {


    suspend fun getMission(type:String): List<GetMissionEntity> {
        return missionRemoteRepository.getMission(type)
    }



}