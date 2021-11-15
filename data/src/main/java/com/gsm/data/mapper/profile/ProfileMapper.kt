package com.gsm.data.maper

import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

// 하지만 반대로 하는 이유는 response 값은 오히려 반대로 data 의 GetProfileResponse 의 값을 domain 의 GetProfileEntity 로 끌어다쓰게 만들어준다.

fun GetProfileResponse.toDomain() : GetProfileEntity {
    return GetProfileEntity(
        this.id,
        this.sub,
        this.name,
        this.profileImg,
        this.isJobSeeker,
        this.companyEmail,
        this.authCompany,
        this.purpose,
        this.level,
        this.exp
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