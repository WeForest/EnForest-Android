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
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentEasyTestBinding
import com.gsm.presentation.databinding.FragmentTestEndBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.test.activity.TestMainActivity
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import com.gsm.presentation.viewmodel.test.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestEndFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding: FragmentTestEndBinding
    private val signViewModel: SignInViewModel by viewModels()
    var token = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_end, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel
        observeToken()
        return binding.root
    }

    fun onClick() {
        testFinish()

        viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                if(findNavController().currentDestination?.id == R.id.testEndFragment2){
                    findNavController().navigate(R.id.action_testEndFragment2_to_testMainFragment2)
                }else{
                startActivity(Intent(requireContext(), MainActivity::class.java))
                (activity as TestMainActivity).finish()
            } }else {
                Toast.makeText(requireContext(), "실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun testFinish() {
        lifecycleScope.launch {
            viewModel.questionCheck(token)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }
}