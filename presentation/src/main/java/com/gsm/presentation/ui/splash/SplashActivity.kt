package com.gsm.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivitySplashBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.sign.`in`.SignInActivity
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readLogin()
        noUseWifi()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


    private fun readLogin() {
        // token 값이 남아있으면 main 남아있지 않다면 다시 로그인 요구
        viewModel.readToken.asLiveData().observe(this, {

            Log.d("splash", "readLogin: ${it.token}")
            if (it.token == viewModel.token) {

                startActivity(Intent(this, SignInActivity::class.java))
                finish()

            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun noUseWifi() {
        //다이얼로그

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        deviceSizeX = size.x
    }

    companion object {
        var deviceSizeX: Int = 0
    }
}