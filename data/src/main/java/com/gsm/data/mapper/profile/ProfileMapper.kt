package com.gsm.data.mapper.profile

import com.gsm.data.entity.profile.request.*
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import com.gsm.domain.entity.request.profile.*
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

// 하지만 반대로 하는 이유는 response 값은 오히려 반대로 data 의 GetProfileResponse 의 값을 domain 의 GetProfileEntity 로 끌어다쓰게 만들어준다.

fun GetProfileResponse.toDomain(): GetProfileEntity {
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

fun PathProfile.toDomain(): PathProfileRequest {
    return PathProfileRequest(
        name = this.name,
        purpose = this.purpose,
        Major =  this.major?.toDomain()!!,
        Interests = this.interests?.toDomain()!!,
        isJobSeeker =this.isJobSeeker,
        companyEmail = this.companyEmail,
    )
}


fun List<InterestsItem>.toDomain(): List<Interests> {

    return this.map {
        Interests(it.interests)
    }

}

@JvmName("toDomainMajorItem")
fun List<MajorItem>.toDomain(): List<Major> {
    return this.map {
        Major(it.major)
    }

}

fun PathProfileResponse.toDomain(): PathProfileEntity {
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