package com.gsm.presentation.ui.userinfo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentUserInfoBinding
import com.gsm.presentation.viewmodel.test.TestViewModel
import com.gsm.presentation.viewmodel.userinfo.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : Fragment() {
    private val viewModel by activityViewModels<UserInfoViewModel>()
    private lateinit var binding : FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)

        getUserInfo("차경민")

        GetProfile("차경민")

        getUserInfoUserFollowing("차경민")

        getUserExpLog("U2FsdGVkX1/+odzQABxvTkkDhxjN8GIs3V6WeZq9wGECzwE7P5k2Z8Wm8/iW9Jgs+aIjl18bC0q4lRZ8iRyLESy4MevH0HUYATA0EXGTE2coyb/kMXyjkz1VeDGFhOxX")

        binding.fragment = this

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.exp = it
        }

        viewModel.data.observe(viewLifecycleOwner) {
            binding.textF = it
        }

        viewModel.dataFollowing.observe(viewLifecycleOwner) {
            binding.text = it
        }

        return binding.root


    }
    fun backClick(){
        findNavController().navigateUp()
    }




    fun onclick(){

        if (findNavController().currentDestination?.id == R.id.userInfoFragment2) {
            findNavController().navigate(R.id.action_userInfoFragment2_to_userActivityFragment2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun GetProfile(userName: String){
        with(viewModel){

            lifecycleScope.launch {
                viewModel.getProfile(userName)
            }

        }
    }

    fun getUserInfoUserFollowing(userName : String){
            with(viewModel){

                lifecycleScope.launch {
                    viewModel.getUserFollowing(userName)
                }

            }
    }

    fun getUserExpLog(authorization : String){
        with(viewModel){

            lifecycleScope.launch {
                viewModel.getUserExpLog(authorization)
            }

        }
    }

    fun getUserInfo(userName : String){
        lifecycleScope.launch{

            with(viewModel){

                lifecycleScope.launch {
                    viewModel.getTest(userName)
                }

            }
        }
    }

}