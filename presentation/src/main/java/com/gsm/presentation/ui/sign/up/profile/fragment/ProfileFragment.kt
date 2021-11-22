package com.gsm.presentation.ui.sign.up.profile.fragment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentProfileBinding
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun FragmentProfileBinding.onViewCreated() {

    }

    override fun FragmentProfileBinding.onCreateView() {
        binding.fragment = this@ProfileFragment
        nameNullTest()
        observeProfile()


    }

    private fun observeProfile() {
        with(viewModel) {
            profileData.observe(requireActivity()) {
                Log.d("TAG", "onCreateView: ${it}")
                binding.data = it


                if (it.isJobSeeker == true) {
                    binding.isCompanyImageView.setBackgroundResource(R.drawable.profile_click_background)
                    binding.isJobSickerImageView.setBackgroundResource(R.drawable.profile_background)
                }else{
                    binding.isCompanyImageView.setBackgroundResource(R.drawable.profile_background)
                    binding.isJobSickerImageView.setBackgroundResource(R.drawable.profile_click_background)
                }
            }
        }
    }

    private fun getProfile(name: String) {


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
                getProfile(it.name)
            }
        }
    }

    fun setProfileButton() {
        startActivity(Intent(requireContext(), SignUpSignInMainActivity::class.java)).apply {

        }

    }


}
