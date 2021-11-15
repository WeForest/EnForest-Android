package com.gsm.data.mapper.mission.remote

import com.gsm.data.entity.mission.response.GetMissionResponse
import com.gsm.domain.entity.mission.GetMissionEntity

fun GetMissionEntity.toDomain(): GetMissionResponse {
    return GetMissionResponse(
        this.content,
        this.createdAt,
        this.exp,
        this.expiredAt,
        this.id,
        this.level,
        this.title,
        this.type,
    )
}

fun toListDomain(data: List<GetMissionResponse>): List<GetMissionEntity> {
    return data.map {

        GetMissionEntity(
            it.content,
            it.createdAt,
            it.exp,
            it.expiredAt,
            it.id,
            it.level,
            it.title,
            it.type,
        )
    }
}
