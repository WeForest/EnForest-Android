package com.gsm.presentation.viewmodel.ai

import android.util.Log
import androidx.lifecycle.*
import com.gsm.presentation.data.AiService
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
}