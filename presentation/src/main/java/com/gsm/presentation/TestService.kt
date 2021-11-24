package com.gsm.presentation

import com.gsm.domain.entity.test.response.GetTestEntity
import retrofit2.http.GET

interface TestService {

    @GET("/question")
    suspend fun viewTest(): GetTestEntity

}