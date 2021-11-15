package com.gsm.data.repository.sign

import com.gsm.data.datasource.sign.LoginDataSourceImpl
import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.entity.response.LoginEntity
import com.gsm.domain.repository.LoginRepository
import com.gsm.data.mapper.sign.toData
import com.gsm.data.mapper.sign.toDomain
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val dataSourceImpl: LoginDataSourceImpl) :
    LoginRepository {
    override suspend fun postLogin(request: TokenEntity): LoginEntity {
        return dataSourceImpl.postLogin(request.toData()).toDomain()
    }
}