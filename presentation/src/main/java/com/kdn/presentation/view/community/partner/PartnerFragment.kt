package com.kdn.presentation.view.community.partner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentPartnerBinding
import com.kdn.presentation.base.BaseFragment
import com.kdn.presentation.widget.dxtension.showVertical

class PartnerFragment : BaseFragment<FragmentPartnerBinding>(R.layout.fragment_partner) {

    private fun initRecyclerView() {
        binding.partnerRecyclerView.showVertical(requireContext())
        binding.partnerRecyclerView.adapter = PartnerRecyclerAdapter()
    }

    override fun init() {
        initRecyclerView()
    }
}