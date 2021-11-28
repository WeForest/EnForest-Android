package com.gsm.presentation.ui.userinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentUserActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivityFragment : Fragment() {

    private lateinit var binding : FragmentUserActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_activity, container, false)

        binding.fragment = this


        return binding.root
    }

    fun back(){

        if (findNavController().currentDestination?.id == R.id.userActivityFragment2) {
            findNavController().navigateUp()
        }
    }

}