package com.gsm.presentation.data

import com.gsm.presentation.data.dto.AbuseResponse
import com.gsm.presentation.data.dto.ConferenceResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AiService {

    @GET("abuse/{data}")
    suspend fun getAbuse(

        @Path("data") data: String,
    ): Response<AbuseResponse>

    @Multipart
    @POST("fileUpload")
    suspend fun postConferenceImage(
        @Part images: MultipartBody.Part?,
    ): Response<ConferenceResponse>

}