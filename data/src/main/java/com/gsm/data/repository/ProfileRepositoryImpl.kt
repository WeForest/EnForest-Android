package com.gsm.data.repository

import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.maper.toDomain
import com.gsm.data.maper.toRequest
import com.gsm.domain.entity.request.GetProfileRequestEntity
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource : ProfileDataSourceImpl
) : ProfileRepository {
    override suspend fun getProfile(request: GetProfileRequestEntity): GetProfileEntity {
        return dataSource.getProfile(request.toRequest()).toDomain()
    }

    override suspend fun pathProfile(authorization: String): PathProfileEntity {
        return dataSource.pathProfile(authorization).toDomain()
    }
}