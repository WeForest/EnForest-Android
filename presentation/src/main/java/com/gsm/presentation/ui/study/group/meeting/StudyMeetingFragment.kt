package com.gsm.presentation.ui.study.group.meeting

import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentStudyMeetingBinding
import com.gsm.presentation.adapter.PartnerRecyclerAdapter
import com.gsm.presentation.util.extension.showVertical
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyMeetingFragment :
    BaseFragment<FragmentStudyMeetingBinding>(R.layout.fragment_study_meeting) {

    override fun FragmentStudyMeetingBinding.onCreateView() {

    }

    override fun FragmentStudyMeetingBinding.onViewCreated() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.studyMeetingRecyclerView.showVertical(requireContext())
        binding.studyMeetingRecyclerView.adapter = PartnerRecyclerAdapter()
    }


}