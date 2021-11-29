package com.gsm.presentation.adapter

import androidx.recyclerview.widget.RecyclerView


import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil

import com.gsm.presentation.R
import com.gsm.presentation.databinding.ChatLayoutBinding
import com.gsm.presentation.databinding.ChattingGroupRecyclerBinding
import com.gsm.presentation.ui.chat.ChatModel
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import java.util.ArrayList

class ChatAdapter( val itemList: ArrayList<ChatModel>) :
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
}