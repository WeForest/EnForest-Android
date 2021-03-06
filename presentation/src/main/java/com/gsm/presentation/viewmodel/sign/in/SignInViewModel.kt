package com.gsm.presentation.viewmodel.sign.`in`

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.usecase.LoginUseCase
import com.gsm.presentation.data.DataStoreRepository
import com.gsm.presentation.util.Constant.Companion.DEFAULT_TOKEN
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStore: DataStoreRepository
) : ViewModel() {


    var token = DEFAULT_TOKEN
    val readToken = dataStore.readToken
    val readImage=dataStore.readImage

    private val _tokenValue = MutableLiveData<Event<String>>()
    val tokenValue: LiveData<Event<String>> get() = _tokenValue
    private val _isLogin = MutableLiveData<Event<Boolean>>()
    val isLogin: LiveData<Event<Boolean>> get() = _isLogin

    // 토큰을 저장한다.
    fun saveToken(token: String) =
        viewModelScope.launch(IO) {
            Log.d("Token", "saveToken: $token")
            dataStore.saveToken(token)
        }

    fun saveProfile(profileImage: String) {
        viewModelScope.launch {
            dataStore.saveProfileImage(profileImage)
        }
    }

    suspend fun postLogin(token: String) = viewModelScope.launch {

        try {
            loginUseCase.buildUseCaseObservable(LoginUseCase.Params(TokenEntity(token))).let {
                Log.d("login", "success ${it.success} message : ${it.message} token  ")
                if (it.success == true) {
                    _tokenValue.value = Event(it.token!!)
                    _isLogin.value = Event(it.isLogin!!)

                } else {
                    _tokenValue.value = Event("")
                }
            }
        } catch (e: Exception) {
            Log.e("login", "postLogin: $e")
            _tokenValue.value = Event("")

        }
    }
}