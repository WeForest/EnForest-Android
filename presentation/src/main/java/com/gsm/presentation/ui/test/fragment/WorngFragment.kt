package com.gsm.presentation.ui.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentTestMainBinding
import com.gsm.presentation.databinding.FragmentWorgBinding
import com.gsm.presentation.viewmodel.test.TestViewModel

class WorngFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentWorgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_worg, container, false)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.fragment = this

        viewModel.data.observe(viewLifecycleOwner) {
            binding.text = it
        }

        return binding.root
    }

    fun ifUserSetAnswer(){
        if(viewModel.isChecked.value == true) {
            Log.d("SDf",viewModel.page.value.toString())
            if(viewModel.page.value == viewModel.wrong.size-1) {
                if (findNavController().currentDestination?.id == R.id.worngFragment) {
                    viewModel.reset()
                    findNavController().navigate(R.id.action_worngFragment_to_testEndFragment)
                }else{
                    findNavController().navigate(R.id.action_worngFragment2_to_worngEndFragment)
                }
            }
            else
            {
                viewModel.getLastClickTextId(binding.radio.checkedRadioButtonId,"틀린문제다시풀기")
            }

        }
        else  Toast.makeText(requireContext(), "우선 정답을 기입해주세요", Toast.LENGTH_SHORT).show()
    }

    fun ifUserSetBackAnswer(){
        if(viewModel.page.value == 0) {
            Toast.makeText(requireContext(), "0번 이하로 갈수없습니다.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.backTest()
        }

    }
}