package com.gsm.presentation.ui.profile.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentProfileBinding
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun FragmentProfileBinding.onViewCreated() {

        Log.d("TAG", "onViewCreated: ")
        isJobSeeker()
    }

    override fun FragmentProfileBinding.onCreateView() {
        binding.fragment = this@ProfileFragment
        nameNullTest()
        with(viewModel){
            profileData.observe(requireActivity()){
                Log.d("TAG", "onCreateView: ${it}")
                binding.data = it

            }
        }


    }

    private fun getProfile(name:String) {


        lifecycleScope.launch {

            with(viewModel) {

                lifecycleScope.launch {

                    getProfile(name)
                }

            }
        }
    }

    private fun nameNullTest() {
        viewModel.readName.asLiveData().observe(viewLifecycleOwner) {
            Log.d("TAG", "nameNullTest: ${it.name}")
            if (it.name.isEmpty()) {
                Toast.makeText(requireContext(), "프로필을 불러올수 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                getProfile(it.name)            }
        }
    }

    fun setProfileButton() {
        findNavController().navigate(R.id.action_setProfileFragment_to_profileFragment)
    }

    private fun isJobSeeker() {
        if (viewModel.profileData.value?.isJobSeeker == true) {
            binding.isCompanyImageView.setColorFilter(R.color.m_c)
            binding.isJobSickerImageView.setColorFilter(R.color.white)
        } else {
            binding.isCompanyImageView.setColorFilter(R.color.white)
            binding.isJobSickerImageView.setColorFilter(R.color.m_c)
        }
    }
}
