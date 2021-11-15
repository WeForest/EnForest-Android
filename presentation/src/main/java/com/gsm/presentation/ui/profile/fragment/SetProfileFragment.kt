package com.gsm.presentation.ui.profile.fragment

import android.content.Intent
import android.content.RestrictionsManager.RESULT_ERROR
import android.icu.number.NumberFormatter.with
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.media.MediaBrowserServiceCompat.RESULT_ERROR
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetProfileFragment : Fragment() {
    private lateinit var binding: FragmentSetProfileBinding
    private var proFileUri: Uri? = null
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile, container, false)

        binding.viewmodel = viewModel
        binding.fragment = this

        getUserProfileAndSetting()

        return binding.root
    }

    fun getUserProfileImage() {
        val intent = Intent()

        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        ImagePicker.with(this)
            .compress(1024)
            .start()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            2404 -> {
                proFileUri = data?.data!!

                binding.profileImageView.setImageURI(proFileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                proFileUri = null
                Toast.makeText(
                    requireContext(),
                    "비정상적인 접근입니다. 다시한번 이미지를 선택해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                Toast.makeText(requireContext(), "작업 취소됨. 다시한번 이미지를 선택해주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun nextButton() {

        if (TextUtils.isEmpty(binding.companyEmailEditText.text) && TextUtils.isEmpty(binding.companyEditText.text)) {
            viewModel.setProfileEmailNameProfile(
                binding.companyEmailEditText.text.toString(),
                binding.companyEditText.text.toString(),
                binding.profileImageView.toString()
            )
            findNavController().navigate(R.id.action_setProfileFragment_to_setProfileEndFragment)
        } else {
            Toast.makeText(activity, "모든 빈칸을 채워주세요!", Toast.LENGTH_SHORT).show()
        }

    }

    fun isJobSeekerOnclick() {


        viewModel.isCompany()
        binding.isCompanyImageView.setColorFilter(R.color.m_c)
        binding.isJobSickerImageView.setColorFilter(R.color.white)
    }


    fun isComponyOnclick() {

        viewModel.isJobSicker()

        binding.isCompanyImageView.setColorFilter(R.color.white)

        binding.isJobSickerImageView.setColorFilter(R.color.m_c)
    }

    private fun getUserProfileAndSetting() {
        val gsa = GoogleSignIn.getLastSignedInAccount(requireContext())

        lifecycleScope.launch {
            viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver {

                with(viewModel) {

                    lifecycleScope.launch {
                        viewModel.getProfile(gsa!!.account!!.name)
                    }

                }
            })
        }
    }
}
