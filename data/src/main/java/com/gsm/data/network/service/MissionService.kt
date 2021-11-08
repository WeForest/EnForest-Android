package com.gsm.data.network.service

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import retrofit2.Response
import retrofit2.http.*

interface MissionService {

    @POST("mission/add")
    suspend fun addMission(
        @Body request: AddMissionRequest
    ): Response<AddMissionResponse>

    @DELETE("mission/{number}")
    suspend fun deleteMission(
        @Path("number") number: Int
    ): Response<DeleteMissioNResponse>

    @GET("mission/{number}")
    suspend fun getMission(
        @Path("number") number: Int
    ): Response<GetMissionResponse>

    @GET("mission/{type}")
    suspend fun getMissionType(
        @Path("type") type: String
    ):Response<GetMissionTypeResponse>
    @PATCH("/mission/clear/{number}")
    suspend fun patchMissionClear(
        @Header("authorization") header: String,
        @Path("number") number: Int
    ): Response<PathMissionClearResponse>

    @PATCH("/mission/fail/{number}")
    suspend fun patchMissionFail(
        @Header("authorization") header: String,
        @Path("number") number: Int
    ): Response<PathMissionFailResponse>

}