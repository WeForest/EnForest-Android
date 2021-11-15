package com.gsm.presentation.ui.study.partner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.presentation.R
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding

class PartnerRecyclerAdapter : RecyclerView.Adapter<PartnerRecyclerAdapter.PartnerRecyclerAdapterViewHolder>() {
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

    }

    override fun getItemCount(): Int {
        return 1
    }

    class PartnerRecyclerAdapterViewHolder( val binding: CommunityRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}

