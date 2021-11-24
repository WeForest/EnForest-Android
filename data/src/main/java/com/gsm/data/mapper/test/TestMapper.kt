package com.gsm.data.mapper.test

import com.gsm.data.entity.test.response.GetTestResponse
import com.gsm.domain.entity.test.response.GetTestEntity

fun GetTestResponse.toDomain(): GetTestEntity {
    return GetTestEntity()
}