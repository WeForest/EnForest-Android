package com.gsm.presentation.util

import android.app.Application
import android.util.Log
import com.gsm.presentation.util.Constant.Companion.Local_SERVER
import dagger.hilt.android.HiltAndroidApp
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

@HiltAndroidApp
class App : Application() {
    companion object {
        private lateinit var socket : Socket
        fun get(): Socket {
            try {
                socket = IO.socket(Local_SERVER)
            } catch (e: URISyntaxException) {
                Log.e("socket", "get: ${e}")
                e.printStackTrace();
            }
            return socket
        }
    }
}