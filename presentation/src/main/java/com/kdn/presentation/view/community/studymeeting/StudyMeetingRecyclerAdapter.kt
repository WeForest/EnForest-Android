package com.kdn.presentation.view.community.studymeeting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.presentation.R
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding
import com.kdn.presentation.view.community.partner.PartnerRecyclerAdapter

class StudyMeetingRecyclerAdapter : RecyclerView.Adapter<StudyMeetingRecyclerAdapter.StudyMeetingRecyclerAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudyMeetingRecyclerAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<CommunityRecyclerViewItemBinding>(
            layoutInflater,
            R.layout.community_recycler_view_item,
            parent,
            false
        )

        return StudyMeetingRecyclerAdapterViewHolder(binding)
    }


    override fun onBindViewHolder(holder: StudyMeetingRecyclerAdapterViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class StudyMeetingRecyclerAdapterViewHolder(private val binding: CommunityRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}