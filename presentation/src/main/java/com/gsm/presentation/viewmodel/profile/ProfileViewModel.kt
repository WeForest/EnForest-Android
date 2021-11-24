package com.gsm.presentation.viewmodel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.request.profile.InterestsItem
import com.gsm.domain.entity.request.profile.MajorItem
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity
import com.gsm.domain.usecase.profile.GetProfileUseCase
import com.gsm.domain.usecase.profile.PathProfileUseCase
import com.gsm.domain.usecase.profile.PostProfileUseCase
import com.gsm.presentation.data.DataStoreRepository
import com.gsm.presentation.util.Constant.Companion.DEFAULT_NAME
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val pathProfileUseCase: PathProfileUseCase,
    private val dataStore: DataStoreRepository,
    private val postProfileUseCase: PostProfileUseCase
) : ViewModel() {

    private val TAG = "profile"
    var defaultName = DEFAULT_NAME
    val readName = dataStore.readName

    //사용자 프로필 저장 변수
    private val _profileData = MutableLiveData<GetProfileEntity>()
    val profileData: LiveData<GetProfileEntity> get() = _profileData
    private val _pathProfileData = MutableLiveData<Event<PathProfileEntity>>()
    val pathProfileData: LiveData<Event<PathProfileEntity>> get() = _pathProfileData

    private val _isJobSeeker = MutableLiveData<Boolean>()
    val isJobSeeker: LiveData<Boolean> get() = _isJobSeeker

    private val _interests = MutableLiveData<MutableList<InterestsItem>>()
    val interests: LiveData<MutableList<InterestsItem>> get() = _interests

    private val _purpose = MutableLiveData<String>()
    val purpose: LiveData<String> get() = _purpose

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _name = MutableLiveData<String?>()
    val name: LiveData<String?> get() = _name

    private val _userProfile = MutableLiveData<String>()
    val userProfile: LiveData<String> get() = _userProfile

    private val _major = MutableLiveData<MutableList<MajorItem>?>()
    val major: LiveData<MutableList<MajorItem>?> get() = _major

    //값이 잘 왔는지 확인하기위한 boolean 값
    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> get() = _isSuccess
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private val _isSuccessValue = MutableLiveData<Event<Boolean>>()
    val isSuccessValue: LiveData<Event<Boolean>> get() = _isSuccessValue

    private fun saveName(name: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "saveName: $name")
            dataStore.saveName(name)
        }


    fun setProfileEmailNameProfile(email: String?, name: String?, profile: String?) {
        this._email.value = email
        this._name.value = name
        this._userProfile.value = profile
    }

    //우선 string 값 한개만 들어가가게 설정

    fun setProfilePurposeMajor(interests: String, major: String) {


        Log.d(TAG, "setProfilePurposeMajor perimeter: ${interests} ${major}")
        _interests.value = listOf(InterestsItem(interests)).toMutableList()
        _major.value = listOf(MajorItem(major)).toMutableList()

        Log.d(TAG, "setProfilePurposeMajor: ${_interests.value?.get(0)} ${_major.value?.get(0)}")

    }

    suspend fun postProfile(token: String, file: MultipartBody.Part) = viewModelScope.launch {
        Log.d(TAG, "postProfile: file ${file}")

        try {
            postProfileUseCase.buildUseCaseObservable(PostProfileUseCase.Params(token, file))
                .apply {
                    Log.d(TAG, "postProfile: ")
                    if (this.success) {
                        Log.d(TAG, "postProfile: file ${file}")
                        _isSuccessValue.value = Event(success)
                        Log.d("file", "postProfile: 성공")
                    }
                }

        } catch (e: Exception) {
            Log.d(TAG, "postProfile: 실패 ${e}")
        }
    }

    private fun getImageBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaType())
        )
    }


    suspend fun getProfile(nickname: String) = viewModelScope.launch {

        Log.d(TAG, "getProfile: $nickname")
        try {
            getProfileUseCase.buildUseCaseObservable(GetProfileUseCase.Params(nickname)).let {
                _profileData.postValue(it)

                _isSuccess.value = Event(true)

                Log.d(TAG, "getProfile: ${it}")
            }


        } catch (e: Exception) {
            Log.d(TAG, "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }

    fun isJobSicker() {
        _isJobSeeker.value = true
    }

    fun isCompany() {
        _isJobSeeker.value = false
    }

    suspend fun pathProfile(
        token: String,
        isJobSeeker: Boolean
    ) = viewModelScope.launch {
        try {
            Log.d(TAG, "pathProfile _isJobSeeker : ${isJobSeeker}")
            pathProfileUseCase.buildUseCaseObservable(
                PathProfileUseCase.Params(
                    token,
                    PathProfile(
                        name = _name.value.toString(),
                        purpose = "",
                        major = _major.value,
                        isJobSeeker = isJobSeeker,
                        interests = _interests.value,
                        companyEmail = _email.value,
                    )
                )
            ).let {
                Log.d(TAG, "pathProfile 성공: ${it}")
                _isSuccess.value = Event(true)
                saveName(_name.value.toString())

                Log.d(TAG, "getMission: clear")
            }


        } catch (e: Exception) {
            Log.d(TAG, "pathProfile: ${e}")

            _isSuccess.value = Event(false)
        }
    }


}