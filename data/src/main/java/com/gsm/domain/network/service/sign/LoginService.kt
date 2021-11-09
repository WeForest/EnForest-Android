package com.gsm.domain.network.service.sign

import com.gsm.domain.entity.sign.request.TokenRequest
import com.gsm.domain.entity.sign.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {

    @POST("account/login")
    suspend fun postLogin(
        @Body request : TokenRequest
    ) : LoginResponse
}