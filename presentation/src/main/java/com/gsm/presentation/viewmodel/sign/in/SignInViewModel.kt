package com.gsm.presentation.viewmodel.sign.`in`

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.request.sign.TokenEntity
import com.gsm.domain.usecase.LoginUseCase
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _successMessage = MutableLiveData<Event<Boolean>>()
    val successMessage: LiveData<Event<Boolean>> get() = _successMessage
    suspend fun postLogin(token: String) = viewModelScope.launch {

        try {
            loginUseCase.buildUseCaseObservable(LoginUseCase.Params(TokenEntity(token))).let {
                if (it.toString().isNotEmpty()) {
                    _successMessage.value = Event(true)

                } else {
                    _successMessage.value = Event(false)
                }
            }
        } catch (e:Exception){
            Log.e("TAG", "postLogin: $e")
            _successMessage.value = Event(false)

        }
    }
}