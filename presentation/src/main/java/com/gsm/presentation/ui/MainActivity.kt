package com.gsm.presentation.ui

import android.os.Bundle
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }
}