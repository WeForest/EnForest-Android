package com.gsm.presentation.util

import android.app.Application
import android.util.Log
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
                socket = IO.socket("http://ec2-3-36-97-97.ap-northeast-2.compute.amazonaws.com:3000")
            } catch (e: URISyntaxException) {
                Log.e("socket", "get: ${e}")
                e.printStackTrace();
            }
            return socket
        }
    }
}