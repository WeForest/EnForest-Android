package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.request.GetProfileRequestEntity
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<GetProfileUseCase.Params,GetProfileEntity>() {

    data class Params(
        val request: GetProfileRequestEntity
    )

    override suspend fun buildUseCaseObservable(params: Params): GetProfileEntity {
        return repository.getProfile(params.request)
    }
}