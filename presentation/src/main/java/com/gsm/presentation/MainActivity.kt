package com.gsm.presentation


import android.os.Bundle
import com.gsm.presentation.base.BaseActivity
import com.kdn.presentation.R
import com.kdn.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }
}