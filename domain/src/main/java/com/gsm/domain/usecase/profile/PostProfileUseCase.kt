package com.gsm.domain.usecase.profile

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class PostProfileUseCase @Inject constructor(private val repository: ProfileRepository) :
    ParamsUseCase<PostProfileUseCase.Params, BaseEntity>(){


    data class Params(
        val token:String,
        val file:  MultipartBody.Part
    )

    override suspend fun buildUseCaseObservable(params: Params): BaseEntity {
        return repository.postProfile(params.token,params.file)
    }
}