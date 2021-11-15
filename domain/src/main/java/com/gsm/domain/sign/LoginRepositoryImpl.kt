package com.gsm.domain.sign

import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val dataSourceImpl: LoginDataSourceImpl) :
    LoginRepository {
    override suspend fun postLogin(request: TokenEntity): LoginEntity {
        return dataSourceImpl.postLogin(request.toData()).toDomain()
    }
}