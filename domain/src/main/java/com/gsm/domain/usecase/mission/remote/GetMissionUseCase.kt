package com.gsm.domain.usecase.mission.remote

import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.repository.MissionRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMissionUseCase @Inject constructor(private val missionRemoteRepository: MissionRemoteRepository) {


     suspend fun getMission(type:String, level:String): List<GetMissionEntity> {
        return missionRemoteRepository.getMission(type,level)
    }



}