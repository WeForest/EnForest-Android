package com.gsm.presentation.viewmodel.sign.`in`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    suspend fun postLogin(token: String) = viewModelScope.launch {

    }
}