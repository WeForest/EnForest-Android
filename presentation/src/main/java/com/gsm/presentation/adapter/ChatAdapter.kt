package com.gsm.presentation.adapter

import androidx.recyclerview.widget.RecyclerView


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil

import com.gsm.presentation.R
import com.gsm.presentation.data.dto.ChatResponse
import com.gsm.presentation.databinding.ChatLayoutBinding
import com.gsm.presentation.ui.chat.ChatModel
import com.gsm.presentation.util.extension.toChatItem
import java.util.ArrayList

class ChatAdapter(var itemList: ArrayList<ChatModel>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ChatLayoutBinding>(
            layoutInflater,
            R.layout.chat_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: ${itemList.size} ")
        return itemList.size
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        // 현재 닉네임과 글쓴이의 닉네임이 같을 경우 배경을 노란색으로 변경
        holder.bind(itemList[position])

    }

    fun addItem(format: ChatModel) {
        itemList.add(format)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ChatLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChatModel) {
            binding.data = data
            binding.executePendingBindings()
        }

    }

    fun setData(data: ChatResponse) {

        Log.d("chat", "setDataToResponse: ${data.toChatItem()}")
        val missionDiffUtil = ChatAdapter.ChatDiffUtil(itemList, data.toChatItem())
        val diffUtilResult = missionDiffUtil.let { DiffUtil.calculateDiff(it) }
        itemList = data.toChatItem() as ArrayList<ChatModel>
        diffUtilResult.dispatchUpdatesTo(this)
    }


    class ChatDiffUtil(
        private val oldList: List<ChatModel>,
        private val newList: List<ChatModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]


        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return getChangePayload(
                oldItemPosition,
                newItemPosition
            )
        }


    }
}