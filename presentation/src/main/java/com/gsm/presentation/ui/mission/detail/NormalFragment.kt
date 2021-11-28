package com.gsm.presentation.ui.mission.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionGetAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionHardBinding
import com.gsm.presentation.databinding.FragmentMissionNormalBinding
import com.gsm.presentation.ui.mission.MissionDetailFragmentArgs
import com.gsm.presentation.ui.mission.MissionFragment
import com.gsm.presentation.viewmodel.mission.MissionRemoteViewModel
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NormalFragment :
    BaseFragment<FragmentMissionNormalBinding>(R.layout.fragment_mission_normal) {


    private val remoteViewModel: MissionRemoteViewModel by viewModels()
    private val missionViewModel: MissionViewModel by activityViewModels()
    private val missionAdapter: MissionGetAdapter by lazy { MissionGetAdapter() }
    override fun FragmentMissionNormalBinding.onViewCreated() {
        setAdapter()
        getMissionData()
        observeMission()
    }



    private fun getMissionData() {

        when (missionViewModel.type.value) {
            MissionFragment.DAY -> {
                lifecycleScope.launch {
                    remoteViewModel.getMission(MissionFragment.DAY, EasyFragment.MIDDLE)
                }
            }
            MissionFragment.WEEK -> lifecycleScope.launch {
                remoteViewModel.getMission(MissionFragment.WEEK, EasyFragment.MIDDLE)
            }
            MissionFragment.MONTH -> lifecycleScope.launch {
                remoteViewModel.getMission(MissionFragment.MONTH, EasyFragment.MIDDLE)
            }

        }
    }

    private fun observeMission() {
        remoteViewModel.getMission.observe(viewLifecycleOwner) {
            if (it != null) {
                missionAdapter.setData(it)
            }
        }
    }

    private fun setAdapter() {
        binding.easyRecyclerView.apply {

            adapter = missionAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2,LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }
    }
}