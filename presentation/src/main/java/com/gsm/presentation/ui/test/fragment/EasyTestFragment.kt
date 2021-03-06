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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.gsm.data.entity.test.response.GetTestItem
import com.gsm.data.entity.test.response.GetTestResponse
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentEasyTestBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.test.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EasyTestFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentEasyTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_easy_test, container, false)

        gogo()

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.fragment = this

        viewModel.data.observe(viewLifecycleOwner) {
            binding.text = it
        }


        return binding.root
    }

    fun gogo(){
        lifecycleScope.launch{

                with(viewModel){

                    lifecycleScope.launch {
                        viewModel.getTest()
                    }

                }
        }
    }

    fun ifUserSetAnswer(){
        if(viewModel.isChecked.value == true) {
            Log.d("SDf",viewModel.page.value.toString())
            if(viewModel.page.value == 19) {
                if (findNavController().currentDestination?.id == R.id.easyTestFragment) {
                    findNavController().navigate(R.id.action_easyTestFragment2_to_testEndFragment2)
                }else{
                    findNavController().navigate(R.id.action_easyTestFragment2_to_testEndFragment2)
                }
            }
            else
            {
                viewModel.getLastClickTextId(binding.radio.checkedRadioButtonId,"????????????","??????")
            }

        }
        else  Toast.makeText(requireContext(), "?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
    }

    fun ifUserSetBackAnswer(){
            Log.d("SDf",viewModel.page.value.toString())
            if(viewModel.page.value == 0) {
                Toast.makeText(requireContext(), "0??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModel.backTest()
            }

        }
    }