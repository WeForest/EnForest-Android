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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentEasyTestBinding
import com.gsm.presentation.databinding.FragmentMiddleTestBinding
import com.gsm.presentation.viewmodel.test.TestViewModel
import kotlinx.coroutines.launch

class MiddleTestFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentMiddleTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_middle_test, container, false)
        gogo()

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.fragment = this

        viewModel.dataM.observe(viewLifecycleOwner) {
            binding.text = it
        }
        return binding.root
    }

    fun gogo(){
        lifecycleScope.launch{

            with(viewModel){

                lifecycleScope.launch {
                    viewModel.getTestM()
                }

            }
        }
    }

    fun ifUserSetAnswer(){
        if(viewModel.isChecked.value == true) {
            Log.d("SDf",viewModel.page.value.toString())
            if(viewModel.page.value == 19) {
                if (findNavController().currentDestination?.id == R.id.middleTestFragment) {
                    findNavController().navigate(R.id.action_middleTestFragment2_to_testEndFragment2)
                }else{
                    findNavController().navigate(R.id.action_middleTestFragment2_to_testEndFragment2)
                }
            }
            else
            {
                viewModel.getLastClickTextId(binding.radio.checkedRadioButtonId,"역량평가","중급")
            }

        }
        else  Toast.makeText(requireContext(), "우선 정답을 기입해주세요", Toast.LENGTH_SHORT).show()
    }

    fun ifUserSetBackAnswer(){
        Log.d("SDf",viewModel.page.value.toString())
        if(viewModel.page.value == 0) {
            Toast.makeText(requireContext(), "0번 이하로 갈수없습니다.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.backTest()
        }

    }
}