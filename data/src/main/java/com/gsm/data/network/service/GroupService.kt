package com.gsm.data.network.service

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.BaseGroupResponse
import com.gsm.data.entity.group.response.CreateGroupResponse
import retrofit2.http.*

interface GroupService {

    // 그룹 가입
    @GET("group/{id}")
    suspend fun joinGroup(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): BaseGroupResponse

    // 그룹 탈퇴
    @PATCH("group/{id}")
    suspend fun secessionGroup(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): BaseGroupResponse

    // 그룹 생성
    @POST("group/create")
    suspend fun createGroup(
        @Header("authorization") token: String,
        @Body request: CreateGroupRequest
    ): CreateGroupResponse

    // 그룹 제거
    @DELETE("group/drop/{id}")
    suspend fun deleteGroup(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): BaseGroupResponse
}