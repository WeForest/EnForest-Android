package com.gsm.presentation.ui.profile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentProfileBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.viewmodel = viewModel
        binding.fragment = this

        isJobSeeker()
        getProfile()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)


        return binding.root
    }

    private fun getProfile(){

        val gsa = GoogleSignIn.getLastSignedInAccount(requireContext())

        lifecycleScope.launch{
            viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver{
                Log.d("TAG", "onViewCreated: success $it")

                with(viewModel){

                    lifecycleScope.launch {
                        viewModel.getProfile(gsa!!.account!!.name)
                    }

                }
            })
        }

    }

    fun setProfileButton(){
        findNavController().navigate(R.id.action_setProfileFragment_to_profileFragment)
    }

    private fun isJobSeeker(){
        if(viewModel.profileData.value!!.isJobSeeker)
        {
            binding.isCompanyImageView.setColorFilter(R.color.m_c)
            binding.isJobSickerImageView.setColorFilter(R.color.white)
        }
        else
        {
            binding.isCompanyImageView.setColorFilter(R.color.white)
            binding.isJobSickerImageView.setColorFilter(R.color.m_c)
        }
    }
}
