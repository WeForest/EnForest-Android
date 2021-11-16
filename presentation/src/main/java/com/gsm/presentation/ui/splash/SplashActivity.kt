package com.gsm.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivitySplashBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.up.activity.SignUpSignInMainActivity
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readLogin()
    }

    private fun readLogin() {
        // token 값이 남아있으면 main 남아있지 않다면 다시 로그인 요구
        viewModel.readToken.asLiveData().observe(this, {

            Log.d("splash", "readLogin: ${it.token}")
            if (it.token == viewModel.token) {

                startActivity(Intent(this, SignUpSignInMainActivity::class.java))
                finish()

            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}