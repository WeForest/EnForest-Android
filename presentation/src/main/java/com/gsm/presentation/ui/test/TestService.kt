package com.gsm.presentation.ui.test

import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.entity.test.response.GetTestEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TestService {

    @GET("/question")
    suspend fun viewTest(): GetTestEntity

    @POST("question/check")
    suspend fun questionCheck(
        @Header("authorization")token:String,
        @Query("ans") number:Int
    ):PathProfileEntity
}