package com.gsm.data.mapper.mission

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.AddMissionResponse
import com.gsm.domain.entity.AddMissionEntity
import com.gsm.domain.entity.request.AddMission


fun AddMissionResponse.toDomain(): AddMissionEntity {
    return AddMissionEntity(
        this.content,
        this.createdAt,
        this.exp,
        this.expiredAt,
        this.id,
        this.level,
        this.title,
        this.type
    )
}

fun AddMissionEntity.toData(): AddMissionResponse {
    return AddMissionResponse(
        this.content,
        this.createdAt,
        this.exp,
        this.expiredAt,
        this.id,
        this.level,
        this.title,
        this.type
    )
}



fun AddMission.toRequest(): AddMissionRequest {
    return AddMissionRequest(
        this.level,
        this.exp,
        this.title,
        this.content,
        this.expiredAt,
        this.type
    )
}
