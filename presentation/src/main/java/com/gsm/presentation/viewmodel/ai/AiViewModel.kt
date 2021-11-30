package com.gsm.presentation.viewmodel.ai

import android.util.Log
import androidx.lifecycle.*
import com.gsm.presentaBroadcastReceivertion.data.AiService
import com.gsm.presentation.data.dto.AbuseResponse
import com.gsm.presentation.data.dto.ConferenceResponse
import com.gsm.presentation.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(

    private val service: AiService
) : ViewModel() {

    private val _conferenceData = MutableLiveData<DataState<ConferenceResponse>>()
    val conferenceData: LiveData<DataState<ConferenceResponse>> get() = _conferenceData

    private val _abuseData = MutableLiveData<DataState<AbuseResponse>>()
    val abuseData: LiveData<DataState<AbuseResponse>> get() = _abuseData

    val TAG = "ai"
    suspend fun postConferenceImage(file: MultipartBody.Part?) = viewModelScope.launch {
        _conferenceData.postValue(DataState.Loading)
        Log.d(TAG, "postConferenceImage: file ${file}")
        try {
            service.postConferenceImage(file).let {
                if (it.body()?.success == true) {
                    if (it.body().toString().isNotEmpty()) {
                        Log.d(TAG, "postConferenceImage: ${it}")
                        _conferenceData.postValue(DataState.Success(it.body()!!))
                    }
                } else {
                    _conferenceData.postValue(DataState.Failure(400, it.message()))
                }

            }

        } catch (e: Exception) {
            Log.d(TAG, "postConferenceImage: ${e}")
            _conferenceData.postValue(DataState.Failure(e.hashCode(), e.message.toString()))
        }
    }

    suspend fun abuseText(text: String) = viewModelScope.launch {
        Log.d(TAG, "abuseText: ${text}")
        _abuseData.postValue(DataState.Loading)
        try {
            service.getAbuse(text).let {
                Log.d(TAG, "abuseText: ${it}")

                _abuseData.postValue(DataState.Success(it.body()!!))
            }
        } catch (e: Exception) {

            Log.d(TAG, "abuseText: ${e}")
            _abuseData.postValue(DataState.Failure(400,"실패했습니다."))
        }

    }
}