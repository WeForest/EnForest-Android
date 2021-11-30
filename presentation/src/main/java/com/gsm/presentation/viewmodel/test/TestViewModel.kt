package com.gsm.presentation.viewmodel.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.test.response.GetTestEntity
import com.gsm.domain.usecase.test.GetTestUseCase
import com.gsm.presentation.R
import com.gsm.presentation.data.TestService
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

    private val _backIsOk = MutableLiveData<Boolean>()
    val backIsOk: LiveData<Boolean> get() = _backIsOk

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> get() = _isChecked

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> get() = _isSuccess

    private val _data = MutableLiveData<GetTestEntity>()
    val data: LiveData<GetTestEntity> get() = _data

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page


    private val _wrong = ArrayList<Int>()
    val wrong : ArrayList<Int> get() = _wrong

    private val _pathProfile = MutableLiveData<Int?>()
     val pathProfile : LiveData<Int?> get()=_pathProfile

    init {
        _backIsOk.value = false
        _page.value = 0
        _isChecked.value = false
        _answerCount.value = 0
    }

    fun isCheck() {
        _isChecked.value = true
    }

    fun getLastClickTextId(answer: Int,position: String) {
        when (answer) {
            R.id.checkBox -> numAdd(1,position)
            R.id.checkBox2 -> numAdd(2,position)
            R.id.checkbox3 -> numAdd(3,position)
            R.id.checkBox4 -> numAdd(4,position)
        }
    }

    fun reset(){
        _page.value= 0
        _answerCount.value = 0
        _backIsOk.value = false
    }

    fun worngReset(){
        _wrong.clear()
    }

    fun backTest(){
        _page.value= _page.value!!.minus(1)

        if(backIsOk.value == true)
            _answerCount.value = _answerCount.value!!.minus(1)
    }

    fun numAdd(answer: Int, position : String) {

        if(position == "역량평가"){
            if (answer == data.value!!.get(page.value!!).answer.toInt() && _page.value!! <= 19) {
                if (page.value == 19) {
                    return
                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == 19) {
                    return
                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                    _wrong.add(page.value!!)
                }

            }
        }
        else if(position == "틀린문제다시풀기"){
            if (answer == data.value!!.get(page.value!!).answer.toInt() && _page.value!! <= wrong.size-1) {
                Log.d("sdddddafd", (wrong.size-1).toString())
                if (page.value == wrong.size-1) {
                    return
                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == wrong.size-1) {
                    return
                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                }

            }
        }

    }

    suspend fun questionCheck(token: String, number: Int) = viewModelScope.launch {
        try {
            service.questionCheck(token,number).let {
                _pathProfile.value=it.exp
                Log.d(TAG, "questionCheck: ${it.exp}")
                _isSuccess.value = Event(true)
            }
        }catch (e:Exception){
            Log.d(TAG, "questionCheck: ${e}")
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