package com.gsm.presentation.ui.mission.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.domain.entity.mission.GetMissionTypePageEntity
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionEasyBinding
import com.gsm.presentation.ui.mission.MissionDetailFragmentArgs
import com.gsm.presentation.ui.mission.MissionFragment.Companion.DAY
import com.gsm.presentation.ui.mission.MissionFragment.Companion.MONTH
import com.gsm.presentation.ui.mission.MissionFragment.Companion.WEEK
import com.gsm.presentation.viewmodel.mission.MissionRemoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EasyFragment : BaseFragment<FragmentMissionEasyBinding>(R.layout.fragment_mission_easy) {
    private val args = MissionDetailFragmentArgs()
    private val remoteViewModel: MissionRemoteViewModel by viewModels()

    private val missionAdapter: MissionAdapter by lazy { MissionAdapter() }
    override fun FragmentMissionEasyBinding.onCreateView() {

    }

    override fun FragmentMissionEasyBinding.onViewCreated() {
        setAdapter()
        getMissionData()
        observeMission()
    }

    private fun getMissionData() {
        when (args.type) {
            DAY -> {
                lifecycleScope.launch {
                    remoteViewModel.getMission(DAY)
                }
            }
            WEEK -> lifecycleScope.launch {
                remoteViewModel.getMission(WEEK)
            }
            MONTH -> lifecycleScope.launch {
                remoteViewModel.getMission(MONTH)
            }

        }
    }

    private fun observeMission() {
        remoteViewModel.getMission.observe(viewLifecycleOwner) {
            missionAdapter.setData(listOf(it as GetMissionTypePageEntity))
        }
    }

    private fun setAdapter() {
        binding.easyRecyclerView.apply {

            adapter = missionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
        }
    }

}