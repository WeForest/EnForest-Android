package com.gsm.data.network.service

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import retrofit2.Response
import retrofit2.http.*

interface MissionService {

    // 미션 추가하기
    @POST("mission/add")
    suspend fun addMission(
        @Body request: AddMissionRequest
    ): AddMissionResponse

    // 미션 제거하기
    @DELETE("mission/{number}")
    suspend fun deleteMission(
        @Path("number") number: Int
    ): DeleteMissioNResponse

    // 미션 갯수로 받기
    @GET("mission/{number}")
    suspend fun getMission(
        @Path("number") number: Int
    ): GetMissionResponse

    @GET    ("mission/{type}/{page}")
    suspend fun getMissionTypePage(
        @Path("type") type: String,
        @Path("page") page: Int
    ):List<GetMissionTypePageResponse>


    @PATCH("/mission/clear/{number}")
    suspend fun patchMissionClear(
        @Header("authorization") header: String,
        @Path("number") number: Int
    ): PathMissionClearResponse

    @PATCH("/mission/fail/{number}")
    suspend fun patchMissionFail(
        @Header("authorization") header: String,
        @Path("number") number: Int
    ): PathMissionFailResponse

}