package com.gsm.presentation.ui.mission.notification

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_MESSAGE
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_TITLE
import kotlinx.coroutines.coroutineScope

class MissionWorkManager(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    override suspend fun doWork(): Result = coroutineScope {

        try {

            val title = inputData.getString(EXTRA_NOTIFICATION_TITLE).toString()
            val message = inputData.getString(EXTRA_NOTIFICATION_MESSAGE).toString()
            Log.d("알람", "doWork: ${title} ${message}")
            // FCM 전송
            NotificationUtil(applicationContext).showNotification(
                title,
                message
            )
            val outPutData=Data.Builder()
                .putString(EXTRA_NOTIFICATION_TITLE,title)
                .putString(EXTRA_NOTIFICATION_MESSAGE,message)
                .build()
            Result.success(outPutData)
        } catch (e: Exception) {
            Result.failure()
        }


    }
}