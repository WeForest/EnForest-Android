package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.presentation.R
import com.gsm.presentation.databinding.ChattingGroupRecyclerBinding
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding
import com.gsm.presentation.ui.study.group.CommunityFragmentDirections

class GroupAdapters :
    PagingDataAdapter<SearchGroupResponseItem, GroupAdapters.ChatGroupRecyclerAdapterViewHolder>(
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
    ): ChatGroupRecyclerAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ChattingGroupRecyclerBinding>(
            layoutInflater,
            R.layout.chatting_group_recycler,
            parent,
            false
        )

        return ChatGroupRecyclerAdapterViewHolder(binding)
    }


    class ChatGroupRecyclerAdapterViewHolder(val binding: ChattingGroupRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bind: SearchGroupResponseItem) {
            binding.data = bind
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(
        holder: ChatGroupRecyclerAdapterViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener { view ->
                val action = item.chattingId?.let { it1 ->
                    CommunityFragmentDirections.actionCommunityFragmentToGroupChatFragment(
                        it1
                    )
                }
                action?.let { view.findNavController().navigate(it) }
            }
        }
    }


}