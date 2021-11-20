package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.domain.entity.group.response.GroupData
import com.gsm.presentation.R
import com.gsm.presentation.databinding.CommunityRecyclerViewItemBinding

class PartnerRecyclerAdapter :
    RecyclerView.Adapter<PartnerRecyclerAdapter.PartnerRecyclerAdapterViewHolder>() {

    private var studyList = mutableListOf<GroupData>()
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

    class PartnerRecyclerAdapterViewHolder(val binding: CommunityRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bind: GroupData) {
            binding.data = bind
            binding.executePendingBindings()
        }
    }

    fun setData(data: List<GroupData>) {

        val groupDiffUtil = GroupDiffUtil(studyList.toList(), (data))
        val diffUtilResult = DiffUtil.calculateDiff(groupDiffUtil)
        studyList = data.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }



    class GroupDiffUtil(
        private val oldList: List<GroupData>,
        private val newList: List<GroupData>
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

