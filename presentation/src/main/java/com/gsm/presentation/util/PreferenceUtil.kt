package com.gsm.presentation.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getInt(key: String, defValue: Int): String {
        return prefs.getInt(key, defValue).toString()
    }

    fun setInt(key: String, str: Int) {
        prefs.edit().putInt(key, str).apply()
    }
}

