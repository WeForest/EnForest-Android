package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.presentation.R
import com.gsm.presentation.data.dto.ConFerenceResponseItem
import com.gsm.presentation.databinding.ConferenceItemBinding

class ConferenceAdapter : RecyclerView.Adapter<ConferenceAdapter.ConFerenceViewHolder>() {

    private var conFerenceList = mutableListOf<ConFerenceResponseItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConFerenceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ConferenceItemBinding>(
            layoutInflater,
            R.layout.conference_item,
            parent,
            false
        )

        return ConFerenceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConFerenceViewHolder, position: Int) {
        holder.bind(conFerenceList[position])
    }

    override fun getItemCount(): Int {
        return conFerenceList.size
    }

    class ConFerenceViewHolder(val binding: ConferenceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ConFerenceResponseItem) {
            binding.data=data
            binding.executePendingBindings()

        }
    }

    fun setData(data: List<ConFerenceResponseItem>) {

        val conFerenceDiffUtil =ConFerenceDiffUtil(conFerenceList, (data))
        val diffUtilResult = conFerenceDiffUtil.let { DiffUtil.calculateDiff(it) }
        conFerenceList = data.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ConFerenceDiffUtil(
        private val oldList: List<ConFerenceResponseItem>,
        private val newList: List<ConFerenceResponseItem>
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

