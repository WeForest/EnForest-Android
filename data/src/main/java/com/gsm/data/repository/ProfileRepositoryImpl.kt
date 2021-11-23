package com.gsm.data.repository

import android.util.Log
import com.gsm.data.datasource.profile.ProfileDataSourceImpl
import com.gsm.data.mapper.group.toDomain
import com.gsm.data.mapper.profile.toDomain
import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: ProfileDataSourceImpl
) : ProfileRepository {
    override suspend fun getProfile(nickname: String): GetProfileEntity {
        return dataSource.getProfile(nickname).toDomain()
    }

    override suspend fun pathProfile(token: String, pathProfile: PathProfile): PathProfileEntity {
        Log.d("profile", "ProfileRepositoryImpl: ${pathProfile} ")
        return dataSource.pathProfile(token, (pathProfile).toDomain()).toDomain()
    }

    override suspend fun postProfile(token: String, file:  MultipartBody.Part): BaseEntity {
        return dataSource.postProfile(token, file).toDomain()
    }

}