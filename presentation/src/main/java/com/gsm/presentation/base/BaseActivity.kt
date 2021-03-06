package com.gsm.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

//BaseActivity.kt
abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) :
    AppCompatActivity() {


    lateinit var binding: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, contentLayoutId)


    }


}