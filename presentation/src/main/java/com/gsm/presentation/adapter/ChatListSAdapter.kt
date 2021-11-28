package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.data.entity.group.response.SearchChatResponseItem
import com.gsm.data.entity.group.response.SearchGroupResponse
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.presentation.R
import com.gsm.presentation.databinding.ChattingGroupRecyclerBinding

class ChatListSAdapter(val onClickListener: RecyclerViewItemClickListener<SearchGroupResponseItem >) :
    PagingDataAdapter<SearchGroupResponseItem, ChatListSAdapter.ChatGroupRecyclerAdapterViewHolder>(
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


    inner class ChatGroupRecyclerAdapterViewHolder(val binding: ChattingGroupRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bind: SearchGroupResponseItem) {
            binding.data = bind
            binding.executePendingBindings()
            binding.layout.setOnClickListener {
                onClickListener.onclick(bind)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ChatGroupRecyclerAdapterViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)

        }
    }


}