package com.gsm.domain.usecase.mission

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.mission.AddMissionEntity
import com.gsm.domain.entity.mission.request.AddMission
import com.gsm.domain.repository.MissionRepository
import javax.inject.Inject

class AddMissionUseCase @Inject constructor(private val repository: MissionRepository) :
    ParamsUseCase<AddMissionUseCase.Params, AddMissionEntity?>() {


    override suspend fun buildUseCaseObservable(params: Params): AddMissionEntity? =
        repository.addMission(params.request)

    data class Params(
        val request: AddMission
    )
}