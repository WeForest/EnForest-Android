package com.gsm.presentation.ui.study.group.partner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.domain.entity.group.response.CreateGroupEntity
import com.gsm.presentation.R
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding

class PartnerRecyclerAdapter : RecyclerView.Adapter<PartnerRecyclerAdapter.PartnerRecyclerAdapterViewHolder>() {

    private val studyList= mutableListOf<CreateGroupEntity>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PartnerRecyclerAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<CommunityRecyclerViewItemBinding>(
            layoutInflater,
            R.layout.community_recycler_view_item,
            parent,
            false
        )

        return PartnerRecyclerAdapterViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PartnerRecyclerAdapterViewHolder, position: Int) {

      holder.bind(studyList[position])
    }

    override fun getItemCount(): Int {
        return studyList.size
    }

    class PartnerRecyclerAdapterViewHolder( val binding: CommunityRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(bind:CreateGroupEntity){
                binding.data=bind
                binding.executePendingBindings()
            }
    }
}

