package com.gsm.data.network.service

import com.gsm.data.entity.test.response.GetTestResponse
import retrofit2.http.GET

interface TestService {

    @GET("/question")
    suspend fun viewTest(): GetTestResponse

}