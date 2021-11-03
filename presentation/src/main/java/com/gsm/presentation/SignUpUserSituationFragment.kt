package com.gsm.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.gsm.presentation.databinding.FragmentSignUpUserSituationBinding

class SignUpUserSituationFragment : BaseFragment<FragmentSignUpUserSituationBinding>(R.layout.fragment_sign_up_user_situation) {

    override fun init() {
        setupSpinnerTag()
        setupSpinnerHandler()
    }

    private fun setupSpinnerTag() {
        val field = resources.getStringArray(R.array.Field)
      //  binding.spinner.adapter=  TagSpinnerAdapter(requireContext(),field)
    }

    private fun setupSpinnerHandler() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                }
            }
        }

    }

}