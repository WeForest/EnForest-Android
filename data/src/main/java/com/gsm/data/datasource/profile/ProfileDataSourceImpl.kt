package com.gsm.data.datasource.profile
import com.gsm.data.base.BaseDataSource
import com.gsm.data.network.service.ProfileService
import com.gsm.data.entity.profile.request.GetProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    override val service: ProfileService
) : BaseDataSource<ProfileService>(), ProfileDataSource {
    override suspend fun getProfile(request: GetProfileRequest): GetProfileResponse {
        return service.viewProfile(request)
    }

    override suspend fun pathProfile(authorization: String): PathProfileResponse {
        return service.pathProfile(authorization)
    }
}