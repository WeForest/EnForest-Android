package com.gsm.data.datasource.sign

import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.sign.request.TokenRequest
import com.gsm.data.entity.sign.response.LoginResponse
import com.gsm.data.network.service.sign.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(override val service: LoginService) :
    BaseDataSource<LoginService>(), LoginDataSource {
    override suspend fun postLogin(request: TokenRequest): LoginResponse {
        return service.postLogin(request)
    }


}