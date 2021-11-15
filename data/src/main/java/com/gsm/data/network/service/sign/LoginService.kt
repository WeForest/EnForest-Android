package com.gsm.data.network.service.sign

import com.gsm.data.entity.sign.request.TokenRequest
import com.gsm.data.entity.sign.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {

    @POST("account/login")
    suspend fun postLogin(
        @Body request : TokenRequest
    ) : LoginResponse
}