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
import com.gsm.domain.usecase.profile.*
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
    private val postProfileUseCase: PostProfileUseCase,
    private val postProfileFollowUseCase: PostProfileFollowUseCase,
    private val unPostProfileUseCase: UnFollowUseCase
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

    fun setProfilePurposeMajor(
        interests: MutableList<InterestsItem>, major: MutableList<MajorItem>?, purpose: String
    ) {


        Log.d(TAG, "setProfilePurposeMajor perimeter: ${interests} ${major}")
        _interests.value = interests as? MutableList<InterestsItem>
        _major.value = major as? MutableList<MajorItem>
        _purpose.value = purpose


    }

    suspend fun postProfile(token: String, file: MultipartBody.Part?) = viewModelScope.launch {
        Log.d(TAG, "postProfile: file ${file} token : ${token}")

        try {
            postProfileUseCase.buildUseCaseObservable(PostProfileUseCase.Params(token, file))
                .apply {
                    Log.d(TAG, "postProfile: ")
                    if (this.success) {
                        Log.d(TAG, "postProfile: file ${this.message}")
                        _isSuccessValue.value = Event(success)
                        Log.d("file", "postProfile: 성공")
                    } else {
                        Log.d(TAG, "postProf ile: 실패 ")
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


    // 사용자 팔로우
    suspend fun postFollow(token: String, nickName: String) = viewModelScope.launch {
        try {
            postProfileFollowUseCase.buildUseCaseObservable(
                PostProfileFollowUseCase.Params(
                    token,
                    nickName
                )
            ).let {
                _isSuccess.value=Event(true)
                Log.d(TAG, "postFollow: 성공")
            }
        }catch (e:Exception){
            _isSuccess.value=Event(false)
            Log.d(TAG, "postFollow: 실패")
        }
    }
    // 사용자 언팔로우
    suspend fun unPostFollow(token: String, nickName: String) = viewModelScope.launch {
        try {
            unPostProfileUseCase.buildUseCaseObservable(
                UnFollowUseCase.Params(
                    token,
                    nickName
                )
            ).let {
                _isSuccess.value=Event(true)
                Log.d(TAG, "postFollow: 성공")
            }
        }catch (e:Exception){
            _isSuccess.value=Event(false)
            Log.d(TAG, "postFollow: 실패")
        }
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
                        purpose = _purpose.value,
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