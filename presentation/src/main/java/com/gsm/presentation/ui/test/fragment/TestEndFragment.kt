package com.gsm.presentation.ui.test.fragment

import android.content.Intent
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
    var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_end, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel
        observeToken()
        observeCount()
        return binding.root
    }

    fun onClick() {
        testFinish()

    }

    private fun testFinish() {
        lifecycleScope.launch {
            Log.d("TAG", "testFinish: ${token}")
            viewModel.questionCheck(token, count)
        }
    }

    private fun observeExp() {
        viewModel.pathProfile.observe(viewLifecycleOwner) {
            if (it != 0) {
                if (findNavController().currentDestination?.id == R.id.testEndFragment2) {
                    viewModel.reset()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    Toast.makeText(requireContext(), "${it}만큼 경험치가 올랐습니다!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.reset()
                    (activity as TestMainActivity).finish()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    Toast.makeText(requireContext(), "${it}만큼 경험치가 올랐습니다!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                viewModel.reset()
                (activity as TestMainActivity).finish()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                Toast.makeText(requireContext(), "한문제도 문제를 맞추시 못했습니다 ㅠㅠ", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }


    private fun observeCount() {
        viewModel.answerCount.observe(viewLifecycleOwner) {
            count = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeExp()

    }

    private fun observeToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }
}