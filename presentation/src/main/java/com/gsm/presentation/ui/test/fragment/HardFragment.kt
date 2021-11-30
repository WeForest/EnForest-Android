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
import com.gsm.presentation.databinding.FragmentHardBinding
import com.gsm.presentation.databinding.FragmentMiddleTestBinding
import com.gsm.presentation.viewmodel.test.TestViewModel
import kotlinx.coroutines.launch

class HardFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentHardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hard, container, false)

        gogo()

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.fragment = this

        viewModel.dataH.observe(viewLifecycleOwner) {
            binding.text = it
        }

        return binding.root
    }

    fun gogo(){
        lifecycleScope.launch{

            with(viewModel){

                lifecycleScope.launch {
                    viewModel.getTestH()
                }

            }
        }
    }

    fun ifUserSetAnswer(){
        if(viewModel.isChecked.value == true) {
            Log.d("SDf",viewModel.page.value.toString())
            if(viewModel.page.value == 9) {
                if (findNavController().currentDestination?.id == R.id.hardFragment) {
                    findNavController().navigate(R.id.action_hardFragment2_to_testEndFragment2)
                }else{
                    findNavController().navigate(R.id.action_hardFragment2_to_testEndFragment2)
                }
            }
            else
            {
                viewModel.getLastClickTextId(binding.radio.checkedRadioButtonId,"역량평가","고급")
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