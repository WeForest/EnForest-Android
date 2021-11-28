package com.gsm.presentation.ui.userinfo.api

import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.entity.userinfo.ExpLog
import com.gsm.domain.entity.userinfo.GetUSerInfoEntityP
import com.gsm.domain.entity.userinfo.GetUserInfoEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserInfoService {

    @GET("/profile/{nickname}/follower")
    suspend fun getUserInfo(@Path("nickname") userid: String): GetUserInfoEntity

    @GET("/profile/{nickname}/following")
    suspend fun getUserInfoFollowing(@Path("nickname")userid: String) : GetUSerInfoEntityP

    @GET("/profile/log")
    suspend fun getUserLog(@Header("authorization")authorization : String) : ExpLog
}