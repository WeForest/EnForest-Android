package com.gsm.domain.usecase.mission

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.AddMissionEntity
import com.gsm.domain.entity.request.AddMission
import com.gsm.domain.repository.MissionRepository
import retrofit2.Response
import javax.inject.Inject

class AddMissionUseCase @Inject constructor(private val repository: MissionRepository) :
    ParamsUseCase<AddMissionUseCase.Params, Response<AddMissionEntity>>() {


    override suspend fun buildUseCaseObservable(params: Params): Response<AddMissionEntity> =
        repository.addMission(params.request)

    data class Params(
        val request:AddMission
    )
}