package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class PathProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PathProfileUseCase.Params, PathProfileEntity>(){


    data class Params(
       val authorization : String
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        return repository.pathProfile(params.authorization)
    }
}