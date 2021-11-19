package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.BaseGroupEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class SecessionGroupUseCase @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<SecessionGroupUseCase.Params, BaseGroupEntity>() {

    data class Params(
        val token: String,
        val id: Int
    )

    override suspend fun buildUseCaseObservable(params: Params): BaseGroupEntity {
        return repository.secessionGroup(params.token, params.id)
    }
}