package com.gsm.domain.sign

import com.gsm.domain.sign.request.TokenRequest
import com.gsm.domain.sign.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {

    @POST("account/login")
    suspend fun postLogin(
        @Body request : TokenRequest
    ) : LoginResponse
}