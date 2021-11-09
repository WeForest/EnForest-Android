package com.gsm.data.mapper.mission

import com.gsm.data.entity.mission.request.AddMissionRequest
import com.gsm.data.entity.mission.response.*
import com.gsm.domain.entity.*
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

fun DeleteMissioNResponse.toDomain(): DeleteMissionEntity {
    return DeleteMissionEntity(
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

fun GetMissionResponse.toDomain(): GetMissionEntity {
    return GetMissionEntity(
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

fun GetMissionTypeResponse.toDomain(): GetMissionTypeEntity {
    return GetMissionTypeEntity(
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

fun PathMissionClearResponse.toDomain(): PathMissionClearEntity {
    return PathMissionClearEntity(

        this.accessToken,
        this.authCompany,
        this.companyEmail,
        this.email,
        this.exp,
        this.id,
        this.isJobSeeker,
        this.level,
        this.name,
        this.profileImg,
        this.purpose,
        this.sub
    )
}


fun PathMissionFailResponse.toDomain(): PachMissionFailEntity {
    return PachMissionFailEntity(

        this.accessToken,
        this.authCompany,
        this.companyEmail,
        this.email,
        this.exp,
        this.id,
        this.isJobSeeker,
        this.level,
        this.name,
        this.profileImg,
        this.purpose,
        this.sub
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
