package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PostConferenceUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PostConferenceUseCase.Params, PathProfileEntity>(){


    data class Params(
        val token:String,
        val file: MultipartBody.Part?
    )

    override suspend fun buildUseCaseObservable(params: Params): PathProfileEntity {
        return repository.postConference(params.token,params.file)
    }
}