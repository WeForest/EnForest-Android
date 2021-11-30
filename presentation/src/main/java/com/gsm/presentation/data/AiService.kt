package com.gsm.presentation.data

import com.gsm.presentation.data.dto.AbuseRequest
import com.gsm.presentation.data.dto.AbuseResponse
import com.gsm.presentation.data.dto.ConferenceResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AiService {

    @GET("abuse")
    suspend fun getAbuse(

       @Query("data")data: String
    ): Response<AbuseResponse>

    @Multipart
    @POST("fileUpload")
    suspend fun postConferenceImage(
        @Part images: MultipartBody.Part?,
    ): Response<ConferenceResponse>

}