package com.gsm.presentation.ui.mission.detail

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.domain.entity.mission.GetMissionTypePageEntity
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionAdapter
import com.gsm.presentation.adapter.MissionGetAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionEasyBinding
import com.gsm.presentation.ui.mission.MissionDetailFragmentArgs
import com.gsm.presentation.ui.mission.MissionFragment.Companion.DAY
import com.gsm.presentation.ui.mission.MissionFragment.Companion.MONTH
import com.gsm.presentation.ui.mission.MissionFragment.Companion.WEEK
import com.gsm.presentation.viewmodel.mission.MissionRemoteViewModel
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EasyFragment : BaseFragment<FragmentMissionEasyBinding>(R.layout.fragment_mission_easy) {

    private val remoteViewModel: MissionRemoteViewModel by viewModels()
    private val missionViewModel: MissionViewModel by activityViewModels()
    private val missionAdapter: MissionGetAdapter by lazy { MissionGetAdapter() }
    override fun FragmentMissionEasyBinding.onCreateView() {

    }

    override fun FragmentMissionEasyBinding.onViewCreated() {
        setAdapter()
        getMissionData()
        observeMission()
    }

    private fun getMissionData() {
        Log.d("TAG", "getMissionData:${missionViewModel.type.value} ")
        when (missionViewModel.type.value) {
            DAY -> {
                lifecycleScope.launch {
                    remoteViewModel.getMission(DAY, LOW)
                }
            }
            WEEK -> lifecycleScope.launch {
                remoteViewModel.getMission(WEEK, LOW)
            }
            MONTH -> lifecycleScope.launch {
                remoteViewModel.getMission(MONTH, LOW)
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)



    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_action_search).isVisible = false
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
        }
    }

    companion object {
        const val LOW = "low"
        const val MIDDLE = "middle"
        const val HIGH = "high"
    }

}