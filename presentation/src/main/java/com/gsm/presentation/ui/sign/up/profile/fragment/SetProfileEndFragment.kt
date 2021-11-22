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
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentSetProfileEndBinding
import com.gsm.presentation.viewmodel.profile.ProfileViewModel

class SetProfileEndFragment : Fragment() {
    private lateinit var binding: FragmentSetProfileEndBinding
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_profile_end, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener{
                setProfileButton()
        }
    }

    private  fun setProfileButton(){
        if(!(TextUtils.isEmpty(binding.companyEditText.text.toString())) && !(TextUtils.isEmpty(binding.InterestsEditText.text.toString()))) {

            Log.d("TAG", "setProfileButton: ${binding.companyEditText.text.toString()} ${(binding.InterestsEditText.text.toString())} ")
            viewModel.setProfilePurposeMajor(
                binding.InterestsEditText.text.toString(),
                binding.companyEditText.text.toString()
            )
            findNavController().navigate(R.id.action_setProfileEndFragment_to_setProfileFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "빈칸을 모두 채워넣어주세요", Toast.LENGTH_SHORT)
                .show()
        }
    }



}