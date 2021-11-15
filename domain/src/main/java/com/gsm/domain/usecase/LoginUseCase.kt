package com.gsm.domain.usecase

import com.gsm.domain.base.ParamsUseCase
import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) :
    ParamsUseCase<LoginUseCase.Params, LoginEntity>() {

    override suspend fun buildUseCaseObservable(params: Params): LoginEntity {
        return repository.postLogin(params.request)
    }

    data class Params(
        val request: TokenEntity
    )

}