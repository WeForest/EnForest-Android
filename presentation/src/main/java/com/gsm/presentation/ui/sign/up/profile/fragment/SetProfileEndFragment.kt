package com.gsm.presentation.ui.sign.up.profile.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsm.domain.entity.request.profile.MajorItem
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileEndBinding
import com.gsm.presentation.ui.sign.up.SignUpSignInMainActivity
import com.gsm.presentation.util.extension.toInterestsItem
import com.gsm.presentation.util.extension.toMajorItem
import com.gsm.presentation.viewmodel.profile.ProfileViewModel

class SetProfileEndFragment : Fragment() {
    private lateinit var binding: FragmentSetProfileEndBinding
    private val viewModel by activityViewModels<ProfileViewModel>()
    private var interestList = mutableListOf<String>()
    private var majorList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile_end, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nextBtn.setOnClickListener {
                setProfileButton()
            }
            backBtn.setOnClickListener {
                (activity as SignUpSignInMainActivity).finish()
            }
        }

    }

    private fun setProfileButton() {
        if (!(TextUtils.isEmpty(binding.interestEditText.text.toString())) && !(TextUtils.isEmpty(
                binding.majorEditText.text.toString()
            ) && !(TextUtils.isEmpty(binding.purposeEditText.text.toString())))
        ) {
            intrestedAdd(binding.interestEditText.text.toString())
            majorAdd(binding.majorEditText.text.toString())

            Log.d(
                "TAG",
                "setProfileButton: ${binding.interestEditText.text.toString()} ${(binding.majorEditText.text.toString())} "
            )
            viewModel.setProfilePurposeMajor(
                interestList.toInterestsItem().toMutableList(),
                majorList.toMajorItem().toMutableList(),
                binding.purposeEditText.text.toString().trim()
            )
            findNavController().navigate(R.id.action_setProfileEndFragment_to_setProfileFragment)
        } else {
            Toast.makeText(requireContext(), "빈칸을 모두 채워넣어주세요", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun intrestedAdd(data: String) {
        interestList = data.trim().split(",").toMutableList()
    }

    private fun majorAdd(data: String) {
        majorList = data.trim().split(",").toMutableList()
    }

}