package com.gsm.presentation.ui.mission.detail
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionGetAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionEasyBinding
import com.gsm.presentation.databinding.FragmentMissionHardBinding
import com.gsm.presentation.ui.mission.MissionDetailFragmentArgs
import com.gsm.presentation.ui.mission.MissionFragment
import com.gsm.presentation.viewmodel.mission.MissionRemoteViewModel
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HardFragment : BaseFragment<FragmentMissionHardBinding>(R.layout.fragment_mission_hard) {
    private val remoteViewModel: MissionRemoteViewModel by viewModels()
    private val missionViewModel: MissionViewModel by activityViewModels()
    private val missionAdapter: MissionGetAdapter by lazy { MissionGetAdapter() }
    override fun FragmentMissionHardBinding.onCreateView() {
        setHasOptionsMenu(true);
    }

    override fun FragmentMissionHardBinding.onViewCreated() {
        setAdapter()
        getMissionData()
        observeMission()
    }


    private fun getMissionData() {
        when (missionViewModel.type.value) {
            MissionFragment.DAY -> {
                lifecycleScope.launch {
                    remoteViewModel.getMission(MissionFragment.DAY, EasyFragment.HIGH)
                }
            }
            MissionFragment.WEEK -> lifecycleScope.launch {
                remoteViewModel.getMission(MissionFragment.WEEK, EasyFragment.HIGH)
            }
            MissionFragment.MONTH -> lifecycleScope.launch {
                remoteViewModel.getMission(MissionFragment.MONTH, EasyFragment.HIGH)
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
        }
    }
}