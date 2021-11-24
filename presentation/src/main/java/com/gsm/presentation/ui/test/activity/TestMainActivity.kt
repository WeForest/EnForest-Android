package com.gsm.presentation.ui.test.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsm.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)
    }
}