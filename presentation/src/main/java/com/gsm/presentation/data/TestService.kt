package com.gsm.presentation.data

import com.gsm.data.entity.profile.response.PathProfileResponse
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.entity.test.response.GetMHTestEntity
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.presentation.data.dto.ChatResponse
import com.gsm.presentation.data.dto.ConFerenceResponseX
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface TestService {

    @GET("/question/low")
    suspend fun viewTest(): GetTestEntity

    @GET("/question/middle")
    suspend fun viewMeddelTest(): GetMHTestEntity

    @GET("/question/high")
    suspend fun viewHighTest(): GetMHTestEntity

    @POST("question/check")
    suspend fun questionCheck(
        @Header("authorization") token: String,
        @Query("ans") number: Int
    ): PathProfileEntity


    @GET("chat/log/{channel}")
    suspend fun getChatLog(
        @Path("channel") channel: Int
    ): ChatResponse


    @GET("profile/{nickname}/conference")
    suspend fun getConference(
        @Path("nickname") nickName: String
    ): Response<ConFerenceResponseX>

    @Multipart
    @PATCH("profile/conference/{name}/{conference}")
    suspend fun pathConference(
        @Header("authorization") token: String,
        @Part images: MultipartBody.Part?,
        @Path("conference") conference:String,
        @Path("name") name:String,
    ): Response<PathProfileResponse>
}