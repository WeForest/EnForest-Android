package com.gsm.presentation.data

import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.presentation.data.dto.ChatResponse
import retrofit2.http.*

interface TestService {

    @GET("/question")
    suspend fun viewTest(): GetTestEntity

    @POST("question/check")
    suspend fun questionCheck(
        @Header("authorization") token: String,
        @Query("ans") number: Int
    ): PathProfileEntity


    @GET("chat/log/{channel}")
    suspend fun getChatLog(
        @Path("channel") channel: Int
    ): ChatResponse
}