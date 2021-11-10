package com.gsm.data.maper

import com.gsm.data.entity.profile.request.GetProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import com.gsm.domain.entity.request.GetProfileRequestEntity
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

// 하지만 반대로 하는 이유는 response 값은 오히려 반대로 data 의 GetProfileResponse 의 값을 domain 의 GetProfileEntity 로 끌어다쓰게 만들어준다.

fun GetProfileResponse.toDomain() : GetProfileEntity {
    return GetProfileEntity(
        this.sub,
        this.name,
        this.profileImg,
        this.isJobSeeker,
        this.companyEmail,
        this.authCompany,
        this.purpose,
        this.level,
        this.exp,
        this.id
    )
}

fun PathProfileResponse.toDomain() : PathProfileEntity {
    return PathProfileEntity(
        this.sub,
        this.name,
        this.profileImg,
        this.isJobSeeker,
        this.companyEmail,
        this.authCompany,
        this.purpose,
        this.level,
        this.exp,
        this.id
    )
}

// domain 딴에 있는 entity 의 값을 data 의 GetProfileRequestEntity 형식으로 끌어다쓰게 만들어준다.

fun GetProfileRequestEntity.toRequest() : GetProfileRequest {
    return GetProfileRequest (
        this.nickname
    )
}