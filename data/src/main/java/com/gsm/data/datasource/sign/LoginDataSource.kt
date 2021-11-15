package com.gsm.data.datasource.sign

import com.gsm.data.entity.sign.request.TokenRequest
import com.gsm.data.entity.sign.response.LoginResponse

interface LoginDataSource {
    suspend fun postLogin(request : TokenRequest) : LoginResponse
}