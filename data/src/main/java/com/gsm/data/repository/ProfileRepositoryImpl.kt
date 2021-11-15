package com.gsm.data.repository

import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.maper.toDomain
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource : ProfileDataSourceImpl
) : ProfileRepository {
    override suspend fun getProfile(nickname: String): GetProfileEntity {
        return dataSource.getProfile(nickname).toDomain()
    }

    override suspend fun pathProfile(pathProfile: PathProfile): PathProfileEntity {
        return dataSource.pathProfile((pathProfile).toDomain()).toDomain()
    }

}