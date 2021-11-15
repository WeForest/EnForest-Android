package com.gsm.presentation.ui.study.partner

import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentPartnerBinding
import com.gsm.presentation.util.extension.showVertical

class PartnerFragment : BaseFragment<FragmentPartnerBinding>(R.layout.fragment_partner) {

    private fun initRecyclerView() {
        binding.partnerRecyclerView.showVertical(requireContext())
        binding.partnerRecyclerView.adapter = PartnerRecyclerAdapter()
    }


}