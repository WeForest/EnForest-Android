package com.gsm.presentation.ui.userinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsm.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }
}