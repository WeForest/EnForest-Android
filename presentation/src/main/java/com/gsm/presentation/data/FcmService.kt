package com.gsm.presentation.data

import com.gsm.presentation.data.dto.PushNotification
import com.gsm.presentation.util.Constant.Companion.CONTENT_TYPE
import com.gsm.presentation.util.Constant.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmService {
    //서버 키와 보낼 형식을 헤더에 넣는다. (json)
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}