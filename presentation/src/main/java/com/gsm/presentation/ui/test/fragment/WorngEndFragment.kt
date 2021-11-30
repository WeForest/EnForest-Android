package com.gsm.presentation.ui.test.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentWorgBinding
import com.gsm.presentation.databinding.FragmentWorngEndBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.test.activity.TestMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.test.TestViewModel

class WorngEndFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentWorngEndBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_worng_end, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    fun onClick() {
                if(findNavController().currentDestination?.id == R.id.worngEndFragment){
                    viewModel.reset()
                    viewModel.worngReset()
                    findNavController().navigate(R.id.action_worngEndFragment_to_testMainFragment2)
                }else{
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    (activity as TestMainActivity).finish()
                }
    }
}