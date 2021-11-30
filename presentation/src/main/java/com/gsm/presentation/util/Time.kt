package com.gsm.presentation.util

import java.util.*

fun getCertainTime(): Long{

    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance().apply {

        set(Calendar.HOUR_OF_DAY, 2)
        set(Calendar.MINUTE, 20)
        set(Calendar.SECOND, 0)

    }

    if (dueDate.before(currentDate))
        dueDate.add(Calendar.HOUR_OF_DAY, 24)

    return dueDate.timeInMillis - currentDate.timeInMillis
}
