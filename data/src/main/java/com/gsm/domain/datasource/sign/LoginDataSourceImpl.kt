package com.gsm.domain.datasource.sign

import com.gsm.domain.base.BaseDataSource
import com.gsm.domain.entity.sign.request.TokenRequest
import com.gsm.domain.entity.sign.response.LoginResponse
import com.gsm.domain.network.service.sign.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(override val service: LoginService) :
    BaseDataSource<LoginService>(), LoginDataSource {
    override suspend fun postLogin(request: TokenRequest): LoginResponse {
        return service.postLogin(request)
    }


}