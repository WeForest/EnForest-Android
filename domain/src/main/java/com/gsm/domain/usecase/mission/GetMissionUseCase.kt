package com.gsm.domain.usecase.mission

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.AddMissionEntity
import com.gsm.domain.entity.GetMissionEntity
import com.gsm.domain.entity.request.AddMission
import com.gsm.domain.repository.MissionRepository
import javax.inject.Inject

class GetMissionUseCase @Inject constructor(private val repository: MissionRepository) :
    ParamsUseCase<GetMissionUseCase.Params, GetMissionEntity?>() {




    override suspend fun buildUseCaseObservable(params: GetMissionUseCase.Params): GetMissionEntity? {
        return repository.getMission(params.number)
    }

    data class Params(
        val number: Int
    )
}