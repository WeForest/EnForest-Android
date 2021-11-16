package com.gsm.domain.usecase.profile

import android.util.Log
import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class PathProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PathProfileUseCase.Params, PathProfileEntity>(){


    data class Params(
        val token:String,
        val pathProfile : PathProfile
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        Log.d("TAG", "buildUseCaseObservable: ${params.pathProfile}")
        return repository.pathProfile(params.token,params.pathProfile)
    }
}