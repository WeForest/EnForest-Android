package com.gsm.domain.mapper.sign

import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.domain.entity.sign.request.TokenRequest
import com.gsm.domain.entity.sign.response.LoginResponse

fun LoginResponse.toDomain(): LoginEntity {
    return LoginEntity(
        this.message,
        this.success,
        this.token
    )
}

fun TokenEntity.toData(): TokenRequest {
    return TokenRequest(
        this.token,

    )
}