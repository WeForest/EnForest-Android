package com.gsm.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gsm.presentation.R

object MissionBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:toInt")
    fun TextView.toInt(data: Int) {
        text = data.toString()
    }



}