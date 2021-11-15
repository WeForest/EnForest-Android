package com.gsm.presentation.ui.sign.up.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gsm.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpUserDetailInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_user_detail_info, container, false)
    }

}