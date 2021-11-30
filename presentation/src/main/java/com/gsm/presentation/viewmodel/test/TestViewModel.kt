package com.gsm.presentation.viewmodel.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsm.domain.entity.test.response.GetMHTestEntity
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

    private val _dataM = MutableLiveData<GetMHTestEntity>()
    val dataM: LiveData<GetMHTestEntity> get() = _dataM

    private val _dataH = MutableLiveData<GetMHTestEntity>()
    val dataH: LiveData<GetMHTestEntity> get() = _dataH

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page


    private val _wrong = ArrayList<Int>()
    val wrong : ArrayList<Int> get() = _wrong

    private val _wrongM = ArrayList<Int>()
    val wrongM : ArrayList<Int> get() = _wrongM

    private val _wrongH = ArrayList<Int>()
    val wrongH : ArrayList<Int> get() = _wrongH

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

    fun getLastClickTextId(answer: Int,position: String,level : String) {
        when (answer) {
            R.id.checkBox -> numAdd(1,position,level)
            R.id.checkBox2 -> numAdd(2,position,level)
            R.id.checkbox3 -> numAdd(3,position,level)
            R.id.checkBox4 -> numAdd(4,position,level)
        }
    }

    fun reset(){
        _page.value= 0
        _answerCount.value = 0
        _backIsOk.value = false
    }

    fun worngReset(){
        _wrong.clear()
        _wrongH.clear()
        _wrongM.clear()
    }

    fun backTest(){
        _page.value= _page.value!!.minus(1)

        if(backIsOk.value == true)
            _answerCount.value = _answerCount.value!!.minus(1)
    }

    fun numAdd(answer: Int, position : String,level: String) {

        if(position == "역량평가" && level == "초급"){
            if (answer == data.value!!.get(page.value!!).answer.toInt() && _page.value!! <= 19) {
                if (page.value == 19) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == 19) {

                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                    _wrong.add(page.value!!)
                }

            }
        }
        else if(position == "역량평가" && level == "중급"){
            if (answer == dataM.value!!.get(page.value!!).answer.toInt() && _page.value!! <= 19) {
                if (page.value == 19) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == 19) {

                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                    _wrongM.add(page.value!!)
                }

            }
        }
        else if(position == "역량평가" && level == "고급"){
            if (answer == dataH.value!!.get(page.value!!).answer.toInt() && _page.value!! <= 9) {
                if (page.value == 9) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == 9) {

                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                    _wrongH.add(page.value!!)
                }

            }
        }
        else if(position == "틀린문제다시풀기" && level == "초급"){
            if (answer == data.value!!.get(page.value!!).answer.toInt() && _page.value!! <= wrong.size-1) {
                Log.d("sdddddafd", (wrong.size-1).toString())
                if (page.value == wrong.size-1) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == wrong.size-1) {

                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                }

            }
        }

        else if(position == "틀린문제다시풀기" && level == "중급"){
            if (answer == dataM.value!!.get(page.value!!).answer.toInt() && _page.value!! <= wrongM.size-1) {
                if (page.value == wrongM.size-1) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == wrongM.size-1) {

                } else {
                    _backIsOk.value = false
                    _page.value = _page.value!!.plus(1)
                }

            }
        }

        else if(position == "틀린문제다시풀기" && level == "고급"){
            if (answer == dataH.value!!.get(page.value!!).answer.toInt() && _page.value!! <= wrongH.size-1) {
                Log.d("sdddddafd", (wrongH.size-1).toString())
                if (page.value == wrongH.size-1) {

                } else {
                    _answerCount.value = _answerCount.value!!.plus(1)
                    _backIsOk.value = true
                    _page.value = _page.value!!.plus(1)
                }
            } else {
                if (page.value == wrongH.size-1) {
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

    suspend fun getTestM() = viewModelScope.launch {

        try {
            service.viewMeddelTest().let {

                _isSuccess.value = Event(true)

                _dataM.value = it

                Log.d(TAG, "getProfile: ${it}")
            }


        } catch (e: Exception) {
            Log.d(TAG, "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }

    suspend fun getTestH() = viewModelScope.launch {

        try {
            service.viewHighTest().let {

                _isSuccess.value = Event(true)

                _dataH.value = it

                Log.d(TAG, "getProfile: ${it}")
            }


        } catch (e: Exception) {
            Log.d(TAG, "fail to : $e")

            _isSuccess.value = Event(false)
        }
    }
}