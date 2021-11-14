package com.gsm.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter

object MissionBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:toInt")
    fun TextView.toInt(data: Int) {
        text=data.toString()
    }
}