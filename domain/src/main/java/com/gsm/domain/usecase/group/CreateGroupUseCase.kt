package com.gsm.domain.usecase.group

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.request.CreateGroup
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase  @Inject constructor(private val repository: GroupRepository) :
    ParamsUseCase<CreateGroupUseCase.Params, CreateGroupEntity>() {

    data class Params(
        val token: String,
        val request: CreateGroup
    )

    override suspend fun buildUseCaseObservable(params: Params): CreateGroupEntity {
        return repository.createGroup(params.token, params.request)
    }
}