package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.domain.entity.mission.GetMissionTypePageEntity
import com.gsm.presentation.R
import com.gsm.presentation.databinding.ItemMissionBinding

class MissionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var missionList = mutableListOf<GetMissionTypePageEntity>()

    companion object {
        const val RECENT_MISSION = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RECENT_MISSION -> RecentMissionViewHolder.from(parent)

            else -> RecentMissionViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is RecentMissionViewHolder -> {
                holder.bind(missionList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return missionList.size
    }

    class RecentMissionViewHolder(val binding: ItemMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GetMissionTypePageEntity) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecentMissionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemMissionBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.item_mission,
                        parent, false
                    )
                return RecentMissionViewHolder(binding)
            }
        }
    }

    fun setData(data: List<GetMissionTypePageEntity>) {

        val movieDiffUtil = MissionDiffUtil(missionList, (data))
        val diffUtilResult = movieDiffUtil.let { DiffUtil.calculateDiff(it) }
        missionList = data.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MissionDiffUtil(
        private val oldList: List<GetMissionTypePageEntity>,
        private val newList: List<GetMissionTypePageEntity>
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