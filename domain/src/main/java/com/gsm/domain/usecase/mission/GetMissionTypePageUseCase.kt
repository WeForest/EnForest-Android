package com.gsm.domain.usecase.mission

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.mission.GetMissionTypePageEntity
import com.gsm.domain.repository.MissionRepository
import javax.inject.Inject

class GetMissionTypePageUseCase @Inject constructor(private val repository: MissionRepository) :
    ParamsUseCase<GetMissionTypePageUseCase.Params, List<GetMissionTypePageEntity?>>() {

    override suspend fun buildUseCaseObservable(params: Params): List<GetMissionTypePageEntity> {
        return repository.getMissionTypePage(params.type,params.page)
    }
    data class Params(
        val type: String,
        val page:Int
    )


}