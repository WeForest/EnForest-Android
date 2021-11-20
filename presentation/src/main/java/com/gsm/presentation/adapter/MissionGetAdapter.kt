package com.gsm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.presentation.R
import com.gsm.presentation.databinding.ItemGetMissionBinding

class MissionGetAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var missionList = mutableListOf<GetMissionEntity>()

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

    class RecentMissionViewHolder(val binding: ItemGetMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GetMissionEntity) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecentMissionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemGetMissionBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.item_get_mission,
                        parent, false
                    )
                return RecentMissionViewHolder(binding)
            }
        }
    }

    fun setData(data: List<GetMissionEntity>) {

        val missionDiffUtil = MissionDiffUtil(missionList, (data))
        val diffUtilResult = missionDiffUtil.let { DiffUtil.calculateDiff(it) }
        missionList = data.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MissionDiffUtil(
        private val oldList: List<GetMissionEntity>,
        private val newList: List<GetMissionEntity>
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