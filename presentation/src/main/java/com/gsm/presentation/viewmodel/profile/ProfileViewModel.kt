package com.gsm.presentation.viewmodel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.request.profile.InterestsItem
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

    private val _isJobSeeker = MutableLiveData<Boolean>()
    val isJobSeeker: LiveData<Boolean> get() = _isJobSeeker

    private val _interests = MutableLiveData<InterestsItem>()
    val interests: LiveData<InterestsItem> get() = _interests

    private val _purpose = MutableLiveData<String>()
    val purpose: LiveData<String> get() = _purpose

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> get() = _email

    private val _name = MutableLiveData<String>()
    val name : LiveData<String> get() = _name

    private val _userProfile = MutableLiveData<String>()
    val userProfile : LiveData<String> get() = _userProfile

    private val _major = MutableLiveData<MajorItem>()
    val major : LiveData<MajorItem> get() = _major

    //값이 잘 왔는지 확인하기위한 boolean 값
    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess : LiveData<Event<Boolean>> get() = _isSuccess

    init {
    _isJobSeeker.value = false

        this._interests.value = InterestsItem("interests")

        this._major.value = MajorItem("marjor")

    }


    fun setProfileEmailNameProfile(email : String, name : String, profile : String){
        this._email.value = email
        this._name.value = name
        this._userProfile.value = profile
    }

    //우선 string 값 한개만 들어가가게 설정

    suspend fun setProfilePurposeMajor(interests: String, major : String){

        val itemInterests = InterestsItem(interests)
        this._interests.value = itemInterests

        val itemMajor = MajorItem(major)
        this._major.value = itemMajor

        pathProfile(_name.value,_purpose.value,_isJobSeeker.value!!,_email.value,this._major.value!!,this._interests.value!!)
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
        _isJobSeeker.value = true
    }

    fun isCompany(){
        _isJobSeeker.value = false
    }

    suspend fun pathProfile(name : String?, purpose : String?, isJobSeeker : Boolean, companyEmail : String?, Major : MajorItem, Interests : InterestsItem
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