package com.gsm.presentation.ui.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentEasyTestBinding
import com.gsm.presentation.databinding.FragmentTestEndBinding
import com.gsm.presentation.viewmodel.test.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestEndFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentTestEndBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_end, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    fun onClick(){
        findNavController().navigate(R.id.action_testEndFragment_to_testMainFragment)
    }
}