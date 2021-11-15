package com.gsm.domain.sign

import com.gsm.domain.sign.request.TokenRequest
import com.gsm.domain.sign.response.LoginResponse

interface LoginDataSource {
    suspend fun postLogin(request : TokenRequest) : LoginResponse
}