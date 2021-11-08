package com.gsm.presentation.util.extension

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class TimeConverter {


    @RequiresApi(Build.VERSION_CODES.O)
    fun addMonth(): Int {
        val current = LocalDateTime.now()
        Log.d("TAG", "addMonth: ${current.month}")
        return when (current.month.name) {
            "JANUARY" -> 28
            "FEBRUARY" -> 31
            "MARCH" -> 30
            "APRIL" -> 31
            "MAY" -> 30
            "JUNE" -> 31
            "JULY" -> 31
            "AUGUST" -> 30
            "SEPTEMBER" -> 31
            "OCTOBER" -> 30
            "NOVEMBER" -> 31
            "DECEMBER" -> 31
            else -> 0
        }
    }

}