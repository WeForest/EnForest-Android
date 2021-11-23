package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class SecessionGroupUseCase @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<SecessionGroupUseCase.Params, BaseEntity>() {

    data class Params(
        val token: String,
        val id: Int
    )

    override suspend fun buildUseCaseObservable(params: Params): BaseEntity {
        return repository.secessionGroup(params.token, params.id)
    }
}