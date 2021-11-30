package com.gsm.presentation.util

import android.app.Application
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

import com.gsm.presentation.ui.mission.notification.MissionWorkManager
import com.gsm.presentation.util.Constant.Companion.CHAT_SERVER
import com.gsm.presentation.util.Constant.Companion.Local_SERVER
import dagger.hilt.android.HiltAndroidApp
import io.socket.client.IO
import io.socket.client.Socket
import java.net.SocketTimeoutException
import java.net.URISyntaxException
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)

//        initWorkManager()
    }

    companion object {
        private lateinit var socket : Socket
        lateinit var prefs: PreferenceUtil

        fun get(): Socket {
            try {
                socket = IO.socket(CHAT_SERVER)
                Log.d("socket", "get: ${socket.connect().connected()}")

            } catch (e: URISyntaxException) {
                Log.e("socket", "get: ${e}")
                e.printStackTrace();
            }catch (e:SocketTimeoutException){
                Log.d("socket", "get: ${e.message}")
            }
            return socket
        }
    }
    private fun initWorkManager() {
        val dailyWorkRequeset = OneTimeWorkRequestBuilder<MissionWorkManager>()
            .setInitialDelay(getTimeUsingInWorkRequest(), TimeUnit.MILLISECONDS)
            .addTag("notify_day_by_day")
            .build()


        WorkManager.getInstance(this).enqueue(dailyWorkRequeset)
    }
}