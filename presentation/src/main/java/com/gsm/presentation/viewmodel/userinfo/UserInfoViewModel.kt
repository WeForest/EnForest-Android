package com.gsm.presentation.viewmodel.userinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.entity.userinfo.ExpLog
import com.gsm.domain.entity.userinfo.GetUSerInfoEntityP
import com.gsm.domain.entity.userinfo.GetUserInfoEntity
import com.gsm.domain.usecase.profile.GetProfileUseCase
import com.gsm.presentation.ui.test.TestService
import com.gsm.presentation.ui.userinfo.api.UserInfoService
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val service: UserInfoService,
    private val usecase : GetProfileUseCase
) : ViewModel() {

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> get() = _isSuccess

    private val _data = MutableLiveData<GetUSerInfoEntityP>()
    val data: LiveData<GetUSerInfoEntityP> get() = _data

    private val _userExp = MutableLiveData<ExpLog>()
    val userExp: LiveData<ExpLog> get() = _userExp

    private val _profile = MutableLiveData<GetProfileEntity>()
    val profile: LiveData<GetProfileEntity> get() = _profile

    private val _dataFollowing = MutableLiveData<GetUserInfoEntity>()
    val dataFollowing: LiveData<GetUserInfoEntity> get() = _dataFollowing

    suspend fun getProfile(nick: String) = viewModelScope.launch {
        try {
            usecase.buildUseCaseObservable(GetProfileUseCase.Params(nick)).let {

                _isSuccess.value = Event(true)

                _profile.value = it

                Log.d("profile", "profile: ${it}")
            }


        } catch (e: Exception) {
            Log.d("profile", "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }

    suspend fun getTest(nick : String) = viewModelScope.launch {


        try {
            service.getUserInfo(nick).let {

                _isSuccess.value = Event(true)

                _dataFollowing.value = it

                Log.d("user_info", "user_info: ${it}")
            }


        } catch (e: Exception) {
            Log.d("user_info", "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }

    suspend fun getUserExpLog(authorization : String) = viewModelScope.launch {

        try {
            service.getUserLog(authorization).let {

                _isSuccess.value = Event(true)

                _userExp.value = it

                Log.d("user_info_exp", "user_info: ${it}")
            }


        } catch (e: Exception) {
            Log.d("user_info_exp", "fail to : $e")

            _isSuccess.value = Event(false)
        }

    }
    
    suspend fun getUserFollowing(nick : String) = viewModelScope.launch {

        try {
            service.getUserInfoFollowing(nick).let {

                _isSuccess.value = Event(true)

                _data.value = it

                Log.d("user_info_fol_df", "user_info: ${it.result}")
            }


        } catch (e: Exception) {
            Log.d("user_info_fol", "fail to : $e")

            _isSuccess.value = Event(false)
        }

    }
}