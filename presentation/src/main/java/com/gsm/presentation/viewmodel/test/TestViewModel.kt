package com.gsm.presentation.viewmodel.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.usecase.test.GetTestUseCase
import com.gsm.presentation.R
import com.gsm.presentation.ui.test.TestService
import com.gsm.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val service: TestService,
    private val getTestUseCase: GetTestUseCase
) : ViewModel() {

    private val TAG = "test1010"

    private val _answerCount = MutableLiveData<Int>()
    val answerCount: LiveData<Int> get() = _answerCount

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> get() = _isChecked

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> get() = _isSuccess

    private val _data = MutableLiveData<GetTestEntity>()
    val data: LiveData<GetTestEntity> get() = _data

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page

    init {
        _page.value = 0
        _isChecked.value = false
        _answerCount.value = 0
    }

    fun isCheck() {
        _isChecked.value = true
    }

    fun getLastClickTextId(answer: Int) {
        when (answer) {
            R.id.checkBox -> numAdd(1)
            R.id.checkBox2 -> numAdd(2)
            R.id.checkbox3 -> numAdd(3)
            R.id.checkBox4 -> numAdd(4)
        }
    }

    fun numAdd(answer: Int) {

        if (answer == data.value!!.get(page.value!!).answer.toInt() && _page.value!! <= 19) {
            _answerCount.value = _answerCount.value!!.plus(1)
            if (page.value == 19) {
                return
            } else {
                _page.value = _page.value!!.plus(1)
            }
        } else {
            if (page.value == 19) {
                return
            } else {
                _page.value = _page.value!!.plus(1)
            }

        }


    }

    suspend fun questionCheck(token: String, number: Int) = viewModelScope.launch {
        try {
            service.questionCheck(token,number).let {
                _isSuccess.value = Event(true)
            }
        }catch (e:Exception){
            _isSuccess.value = Event(false)

        }
    }

    suspend fun getTest() = viewModelScope.launch {

        try {
            service.viewTest().let {

                _isSuccess.value = Event(true)

                _data.value = it

                Log.d(TAG, "getProfile: ${it}")
            }


        } catch (e: Exception) {
            Log.d(TAG, "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }
}