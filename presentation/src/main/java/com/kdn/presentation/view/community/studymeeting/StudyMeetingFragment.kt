package com.kdn.presentation.view.community.studymeeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentStudyMeetingBinding
import com.kdn.presentation.base.BaseFragment
import com.kdn.presentation.view.community.partner.PartnerRecyclerAdapter
import com.kdn.presentation.widget.dxtension.showVertical

class StudyMeetingFragment : BaseFragment<FragmentStudyMeetingBinding>(R.layout.fragment_study_meeting) {
    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.studyMeetingRecyclerView.showVertical(requireContext())
        binding.studyMeetingRecyclerView.adapter = PartnerRecyclerAdapter()
    }
}