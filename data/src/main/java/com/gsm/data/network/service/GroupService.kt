package com.gsm.data.network.service

import com.gsm.data.entity.group.request.CreateGroupRequest
import com.gsm.data.entity.group.response.*
import retrofit2.http.*

interface GroupService {

    // 그룹 가입
    @GET("group/{id}")
    suspend fun joinGroup(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): JoinGroupResponse

    // 그룹 탈퇴
    @PATCH("group/{id}")
    suspend fun secessionGroup(
        @Header("authorization") token: String,
        @Path("id") id: Int
    ): BaseResponse

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
    ): BaseResponse

    @GET("group/list/{page}")
    suspend fun searchGroup(
        @Path("page") page:Int,
        @Query("k") keyword:String
    ):List<SearchGroupResponse>
    @GET("group/list/{page}")
    suspend fun searchGroups(
        @Path("page") page:Int,
        @Query("k") keyword:String?
    ):SearchGroupResponse
    //chat 그룹
    @GET("chat/{page}")
    suspend fun searchChat(
        @Path("page") page:Int,
        @Query("k") key:String,
    ):SearchChatResponse
}