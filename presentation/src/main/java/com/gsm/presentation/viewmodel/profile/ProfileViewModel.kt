package com.gsm.presentation.viewmodel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.request.profile.Interests
import com.gsm.domain.entity.request.profile.InterestsItem
import com.gsm.domain.entity.request.profile.Major
import com.gsm.domain.entity.request.profile.MajorItem
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.usecase.profile.GetProfileUseCase
import com.gsm.domain.usecase.profile.PathProfileUseCase
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val pathProfileUseCase : PathProfileUseCase
) : ViewModel() {

    private val TAG = "profile"

    //사용자 프로필 저장 변수
    private val _profileData = MutableLiveData<GetProfileEntity>()
    val profileData: LiveData<GetProfileEntity> get() = _profileData

    private var name : String? = null

    private var isJobSeeker : Boolean = true

    private lateinit var major : Major

    private lateinit var interests : Interests

    private var purpose : String = ""

    private var email : String = ""

    private var userProfile : String? = null

    //값이 잘 왔는지 확인하기위한 boolean 값
    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess : LiveData<Event<Boolean>> get() = _isSuccess


    fun setProfileEmailNameProfile(email : String, name : String, profile : String){
        this.email = email
        this.name = name
        this.userProfile = profile
    }

    //우선 string 값 한개만 들어가가게 설정

    suspend fun setProfilePurposeMajor(interests: String, major : String){

        val itemInterests = InterestsItem(interests)
        this.interests[0] = itemInterests

        val itemMajor = MajorItem(major)
        this.major[0] = itemMajor

        pathProfile(name,purpose,isJobSeeker,email,this.major,this.interests)
    }


    suspend fun getProfile(nickname: String) = viewModelScope.launch {

        try {
            getProfileUseCase.buildUseCaseObservable(GetProfileUseCase.Params(nickname)).let {
                _profileData.value = it

                _isSuccess.value = Event(true)

                Log.d(TAG, "getMission: clear")
            }


        } catch (e: Exception) {
                Log.d(TAG,"fail to : $e")

            _isSuccess.value = Event(false)
        }
    }

    fun isJobSicker(){
        isJobSeeker = true
    }

    fun isCompany(){
        isJobSeeker = false
    }

    suspend fun pathProfile(name : String?, purpose : String?, isJobSeeker : Boolean, companyEmail : String?, Major : Major, Interests : Interests
    ) = viewModelScope.launch {

        try {
            pathProfileUseCase.buildUseCaseObservable(PathProfileUseCase.Params(name,purpose, isJobSeeker, companyEmail, Major, Interests)).let {

                _isSuccess.value = Event(true)

                Log.d(TAG, "getMission: clear")
            }


        } catch (e: Exception) {
            Log.d(TAG,"fail to : $e")

            _isSuccess.value = Event(false)
        }
    }


}