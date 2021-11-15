package com.gsm.domain.sign

import com.gsm.domain.base.BaseDataSource
import com.gsm.domain.sign.request.TokenRequest
import com.gsm.domain.sign.response.LoginResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(override val service: LoginService) :
    BaseDataSource<LoginService>(), LoginDataSource {
    override suspend fun postLogin(request: TokenRequest): LoginResponse {
        return service.postLogin(request)
    }


}