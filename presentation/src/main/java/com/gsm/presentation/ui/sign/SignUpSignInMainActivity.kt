package com.gsm.presentation.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsm.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSignInMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_sign_in_main)
    }
}