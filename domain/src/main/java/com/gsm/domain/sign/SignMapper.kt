package com.gsm.domain.sign

import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.domain.sign.request.TokenRequest
import com.gsm.domain.sign.response.LoginResponse

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