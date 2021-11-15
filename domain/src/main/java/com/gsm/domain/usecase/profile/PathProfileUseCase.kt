package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.request.profile.Interests
import com.gsm.domain.entity.request.profile.Major
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class PathProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PathProfileUseCase.Params, PathProfileEntity>(){


    data class Params(
        val name : String?,
        val purpose : String?,
        val isJobSeeker : Boolean,
        val companyEmail : String?,
        val Major : Major,
        val Interests : Interests
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        return repository.pathProfile(params.name,params.purpose,params.isJobSeeker,params.companyEmail,params.Major,params.Interests)
    }
}