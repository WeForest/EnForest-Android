package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UnFollowUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<UnFollowUseCase.Params, PathProfileEntity>(){


    data class Params(
        val token:String,
        val nickName : String
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        return repository.unPostFollow(params.token,params.nickName)
    }
}