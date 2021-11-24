package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.presentation.R
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding
import com.gsm.presentation.ui.study.group.CommunityFragmentDirections

class GroupRecyclerAdapter :
    PagingDataAdapter<SearchGroupResponseItem, GroupRecyclerAdapter.PartnerRecyclerAdapterViewHolder>(
        diffCallback
    ) {
    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<SearchGroupResponseItem>() {
            override fun areItemsTheSame(
                oldItem: SearchGroupResponseItem,
                newItem: SearchGroupResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SearchGroupResponseItem,
                newItem: SearchGroupResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

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
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                val action = item.chattingId?.let { it1 ->
                    CommunityFragmentDirections.actionCommunityFragmentToGroupChatFragment(
                        it1
                    )
                }
                action?.let { it1 -> it.findNavController().navigate(it1) }
            }
        }
    }


    class PartnerRecyclerAdapterViewHolder(val binding: CommunityRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bind: SearchGroupResponseItem) {
            binding.data = bind
            binding.executePendingBindings()
        }
    }


}


