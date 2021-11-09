package com.gsm.domain.repository

import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity

interface LoginRepository {

    suspend fun postLogin(request: TokenEntity) : LoginEntity
}