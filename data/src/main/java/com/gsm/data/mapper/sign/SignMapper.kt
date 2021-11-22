package com.gsm.data.mapper.sign

import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.data.entity.sign.request.TokenRequest
import com.gsm.data.entity.sign.response.LoginResponse

fun LoginResponse.toDomain(): LoginEntity {
    return LoginEntity(
        this.message,
        this.success,
        this.token,
        this.isLogin
    )
}

fun TokenEntity.toData(): TokenRequest {
    return TokenRequest(
        this.token,

    )
}