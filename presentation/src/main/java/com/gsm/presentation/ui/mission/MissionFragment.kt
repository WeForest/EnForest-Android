package com.gsm.presentation.ui.mission

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.gsm.domain.entity.mission.GetMissionEntity
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionBinding
import com.gsm.presentation.ui.mission.notification.MissionBroadcastReceiver
import com.gsm.presentation.util.App
import com.gsm.presentation.util.Constant.Companion.ALARM_TIMER
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_MESSAGE
import com.gsm.presentation.util.Constant.Companion.EXTRA_NOTIFICATION_TITLE
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.mission.MissionRemoteViewModel
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


/*
todo : 하루에 하나씩 미션이 울려 저장을 해야함 내부 db 사용 헤애힘 !!
 */
@AndroidEntryPoint
class MissionFragment : BaseFragment<FragmentMissionBinding>(R.layout.fragment_mission) {
    var title = ""
    var message = ""
    private val args by navArgs<MissionFragmentArgs>()
    private val missionAdapter: MissionAdapter by lazy { MissionAdapter() }
    private val viewModel: MissionViewModel by viewModels<MissionViewModel>()
    private val remoteViewModel: MissionRemoteViewModel by viewModels<MissionRemoteViewModel>()
    override fun FragmentMissionBinding.onCreateView() {
        setHasOptionsMenu(false)
        binding.fragment = this@MissionFragment


        with(binding) {
            missionDailyLayout.setOnClickListener {

                val action = MissionFragmentDirections
                    .actionMissionFragmentToMissionDetailFragment(DAY)
                it?.findNavController()?.navigate(action)
            }
            missionWeeklyLayout.setOnClickListener {

                val action = MissionFragmentDirections
                    .actionMissionFragmentToMissionDetailFragment(WEEK)
                it?.findNavController()?.navigate(action)

            }
            missionMonthlyLayout.setOnClickListener {
                val action = MissionFragmentDirections
                    .actionMissionFragmentToMissionDetailFragment(MONTH)
                it?.findNavController()?.navigate(action)
            }
        }
    }

    var chipText = "daily"

    var number: Int = 1

    override fun FragmentMissionBinding.onViewCreated() {
        getMissionType(DAY, 1)
        chipClickType()
        setAdapter()

        observeGetMissionType()
        observeErrorMessage()

        getMission()
        observeGetMission()
        onArgs()
        observeMissionNumber()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onArgs() {
        if (args.data) {
            missionAdapter.notifyDataSetChanged()
        }
    }

    private fun setAlarm(title: String, message: String) {
        val triggerTime = (SystemClock.elapsedRealtime() // 기기가 부팅된 후 경과한 시간 사용
                + ALARM_TIMER * 1000)
        val repeatInterval = AlarmManager.INTERVAL_DAY
        val alarmMgr = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Log.d("알람", "onViewCreated: ${title}, ${message}")
        val alarmIntent =
            Intent(requireActivity(), MissionBroadcastReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_NOTIFICATION_TITLE, title)
                intent.putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
                PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,

                repeatInterval, //하루마다
                alarmIntent
            )
            Log.d("알람", "setAlarm: ${repeatInterval / 60000}분 마다 알림이 발생합니다.\"")
        } else {
            alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                repeatInterval,
                alarmIntent
            )
        }
    }


    private fun getMissionType(type: String, page: Int) {
        lifecycleScope.launch {
            viewModel.getMissionType(type, page)
        }
    }

    // 각각의 type에 adapter를 refresh 해준다.
    private fun chipClickType() {
        binding.chipType.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            chipText = selectedMealType
            when (chipText) {
                DAY -> getMissionType(DAY, 1)
                WEEK -> getMissionType(WEEK, 1)
                MONTH -> getMissionType(MONTH, 1)
            }
        }
    }


    private fun observeGetMissionType() {
        viewModel.missionPageData.observe(viewLifecycleOwner, EventObserver {
            when (it[0].type) {
                DAY -> missionAdapter.setData(it)
                WEEK -> missionAdapter.setData(it)
                MONTH -> missionAdapter.setData(it)
            }
        })
    }


    private fun setAdapter() {
        binding.recyclerView.apply {

            adapter = missionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getMission() {
        lifecycleScope.launch {

            val data = App.prefs.getInt("number", number)
            Log.d("mission", "observeGetMission: getNumber ${data}")

            Log.d("알람", "getMission: mission")
            viewModel.getMission(data.toInt())


        }
    }

    private fun observeGetMission() {
        viewModel.missionData.observe(viewLifecycleOwner) {
            Log.d("알람", "observeGetMission: ${it.title} ${it.content}")
            title = it.title.toString()
            message = it.content.toString()
            setAlarm(title, message)
            Log.d("mission", "observeGetMission: setNumber ${number}")


            App.prefs.setInt("number", number++)
            insertMission(it)
        }
    }

    private fun observeMissionNumber() {
        with(viewModel) {
            success.observe(viewLifecycleOwner, EventObserver {
                if(!it){
                    Log.d("mission", "observeMissionNumber: ${number} ")
                    App.prefs.setInt("number", 1)
                }
            })
        }
    }


    private fun insertMission(getMissionEntity: GetMissionEntity) {
        lifecycleScope.launch {
            remoteViewModel.insertMission(getMissionEntity)
        }
    }

    private fun observeErrorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Log.d("TAG", "observeErrorMessage: error")
                binding.recyclerView.visibility = View.INVISIBLE
                binding.errorLayout.visibility = View.VISIBLE

            } else {
                Log.d("TAG", "observeErrorMessage: not error")
                binding.recyclerView.visibility = View.VISIBLE
                binding.errorLayout.visibility = View.GONE
            }
        }
    }

    fun writeLayoutClick(view: View?) {
        // 현재 navigation 위치가 mssionFragment 일때만 화면이동한다.

        if (findNavController().currentDestination?.id == R.id.missionFragment)
            findNavController().navigate(
                R.id.action_fragmentMission_to_bottomSheetMissionDialog
            )

    }

    companion object {
        const val DAY = "daily"
        const val WEEK = "weekly"
        const val MONTH = "monthly"
    }
}