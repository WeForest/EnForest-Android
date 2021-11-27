package com.gsm.domain.usecase.profile

import android.util.Log
import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class PostProfileFollowUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PostProfileFollowUseCase.Params, PathProfileEntity>(){


    data class Params(
        val token:String,
        val nickName : String
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        return repository.postFollow(params.token,params.nickName)
    }
}