package com.gsm.presentation.ui.userinfo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentUserInfoBinding
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import com.gsm.presentation.viewmodel.test.TestViewModel
import com.gsm.presentation.viewmodel.userinfo.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : Fragment() {
    private val viewModel by activityViewModels<UserInfoViewModel>()
    private val profileViewModel :ProfileViewModel by viewModels()
    private val signViewModel :SignInViewModel by viewModels()
    private lateinit var binding : FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)




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

    private fun observeNickName(){
         profileViewModel.readName.asLiveData().observe(viewLifecycleOwner){

             getUserInfo(it.name)

             GetProfile(it.name)

             getUserInfoUserFollowing(it.name)
         }
    }
    private fun observeToken(){
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner){
            getUserExpLog(it.token)

        }
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
        observeNickName()
        observeToken()
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