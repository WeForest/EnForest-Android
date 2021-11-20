package com.gsm.presentation.ui.study.group.partner

import androidx.fragment.app.activityViewModels
import com.gsm.presentation.R
import com.gsm.presentation.adapter.GroupRecyclerAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentPartnerBinding
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartnerFragment : BaseFragment<FragmentPartnerBinding>(R.layout.fragment_partner) {

    private val groupAdapter:GroupRecyclerAdapter by lazy{
        GroupRecyclerAdapter()
    }
    private val viewModel : GroupViewModel by activityViewModels()
    private fun initRecyclerView() {
        binding.partnerRecyclerView.showVertical(requireContext())
        binding.partnerRecyclerView.adapter = groupAdapter
    }

    override fun FragmentPartnerBinding.onCreateView() {

    }

    override fun FragmentPartnerBinding.onViewCreated() {
        initRecyclerView()
        with(viewModel){

        }
    }



}