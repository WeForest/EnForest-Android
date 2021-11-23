package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.JoinGroupEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<JoinGroupUseCase.Params, JoinGroupEntity>() {

    data class Params(
        val token: String,
        val id: Int
    )

    override suspend fun buildUseCaseObservable(params: Params): JoinGroupEntity {
        return repository.joinGroup(params.token, params.id)
    }
}