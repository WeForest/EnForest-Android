package com.gsm.data.datasource.profile
import android.util.Log
import com.gsm.data.base.BaseDataSource
import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.network.service.ProfileService
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    override val service: ProfileService
) : BaseDataSource<ProfileService>(), ProfileDataSource {

    override suspend fun getProfile(nickname: String): GetProfileResponse {
        return service.viewProfile(nickname)
    }

    override suspend fun pathProfile(token:String,request: PathProfileRequest): PathProfileResponse {
        Log.d("ProfileDataSourceImpl", "pathProfile: ${request} ")
        return service.pathProfile(token,request)
    }
}