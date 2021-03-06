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
import com.gsm.presentation.data.TestService
import com.gsm.presentation.data.dto.ChatResponse
import com.gsm.presentation.data.dto.ConFerenceRequest
import com.gsm.presentation.data.dto.ConFerenceResponseX
import com.gsm.presentation.data.dto.ConferenceResponse
import com.gsm.presentation.util.Constant.Companion.DEFAULT_NAME
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Part
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val pathProfileUseCase: PathProfileUseCase,
    private val dataStore: DataStoreRepository,
    private val postProfileUseCase: PostProfileUseCase,
    private val postProfileFollowUseCase: PostProfileFollowUseCase,
    private val unPostProfileUseCase: UnFollowUseCase,
    private val postConferenceUseCase: PostConferenceUseCase,
    private val testService: TestService,

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

    private val _chatLog = MutableLiveData<ChatResponse>()
    val chatLog: LiveData<ChatResponse> get() = _chatLog


    private val _isSuccessValue = MutableLiveData<Event<Boolean>>()
    val isSuccessValue: LiveData<Event<Boolean>> get() = _isSuccessValue
    private val _conferenceData = MutableLiveData<ConFerenceResponseX>()
    val conferenceData: LiveData<ConFerenceResponseX> get() = _conferenceData

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url
    private fun saveName(name: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "saveName: $name")
            dataStore.saveName(name)
        }

    fun getChatLog(patch: Int) = viewModelScope.launch {

        try {
            testService.getChatLog(patch).let {
                Log.d(TAG, "getChatLog: ${it}")
                _chatLog.value = it
            }
        } catch (e: Exception) {
            Log.d("chat", "getChatLog: ${e}")
        }
    }

    suspend fun getConference(nickName: String) = viewModelScope.launch {
        try {
            testService.getConference(nickName).let {

                Log.d(TAG, "getConference: ${it.body()?.get(0)}")
                _conferenceData.value = it.body()

            }
        } catch (e: Exception) {
            Log.d(TAG, "getConference: ${e}")
        }
    }

    suspend fun pathConference(
        token: String,
        images: MultipartBody.Part?,
        confernce: String,
        name: String
    ) =
        viewModelScope.launch {
            try {
                testService.pathConference(token, images, confernce, name).let {
                    Log.d(TAG, "pathConference: ${it}")
                    Log.d(TAG, "pathConference: 성공")
                }
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "pathConference: $e")
            }
        }

    suspend fun postConference(token: String, file: MultipartBody.Part) = viewModelScope.launch {
        try {

            postConferenceUseCase.buildUseCaseObservable(PostConferenceUseCase.Params(token, file))
                .let {
                    Log.d(TAG, "postConference: ${it}")
                }
        } catch (e: Exception) {

        }
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
                        _url.value = this.message
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
                _isSuccess.value = Event(true)
                Log.d(TAG, "postFollow: 성공")
            }
        } catch (e: Exception) {
            _isSuccess.value = Event(false)
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
                _isSuccess.value = Event(true)
                Log.d(TAG, "postFollow: 성공")
            }
        } catch (e: Exception) {
            _isSuccess.value = Event(false)
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
                _pathProfileData.value = Event(it)

                Log.d(TAG, "getMission: clear")
            }


        } catch (e: Exception) {
            Log.d(TAG, "pathProfile: ${e}")

            _isSuccess.value = Event(false)
        }
    }


}