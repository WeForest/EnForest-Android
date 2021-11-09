package com.gsm.domain.datasource.sign

import com.gsm.domain.entity.sign.request.TokenRequest
import com.gsm.domain.entity.sign.response.LoginResponse

interface LoginDataSource {
    suspend fun postLogin(request : TokenRequest) : LoginResponse
}