package com.gsm.data.network.service
import com.gsm.data.entity.profile.response.GetNickNameResopnse
import retrofit2.http.*

interface ProfileService {

    @GET("/profile/{nickname}")
    suspend fun viewProfile(
        @Path("nickname") nickname: String
    ): GetNickNameResopnse

}