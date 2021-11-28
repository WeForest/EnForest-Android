package com.gsm.presentation.ui.mission.notification

import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_MESSAGE
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_TITLE
import java.util.concurrent.TimeUnit

class MissionBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {

            val title = it.getStringExtra(EXTRA_NOTIFICATION_TITLE).toString()
            val message = it.getStringExtra(EXTRA_NOTIFICATION_MESSAGE).toString()
            Log.d("TAG", "onReceive: 알람 $title,  $message ")
            // Create Notification Data
            val notificationData = Data.Builder()
                .putString(EXTRA_NOTIFICATION_TITLE, "새로운 미션이 도착했습니다.")
                .putString(EXTRA_NOTIFICATION_MESSAGE, "코딩테스트 1문제 풀기")
                .build()

            // WorkManager 사용
            val workRequest =
                OneTimeWorkRequestBuilder<MissionWorkManager>()
                    .setInputData(notificationData)
                    .setBackoffCriteria(BackoffPolicy.LINEAR, 10000, TimeUnit.MILLISECONDS)
                    .build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(workRequest)
        }
    }
}