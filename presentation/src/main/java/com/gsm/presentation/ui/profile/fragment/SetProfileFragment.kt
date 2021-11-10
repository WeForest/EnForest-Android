package com.gsm.presentation.ui.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentProfileBinding
import com.gsm.presentation.databinding.FragmentSetProfileBinding
import com.gsm.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetProfileFragment : Fragment() {
    private lateinit var binding : FragmentSetProfileBinding
    private val signInViewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile, container, false)

        return binding.root
    }

}