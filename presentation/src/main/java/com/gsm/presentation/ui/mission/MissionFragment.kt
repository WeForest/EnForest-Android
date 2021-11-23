package com.gsm.presentation.ui.mission

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.gsm.presentation.R
import com.gsm.presentation.adapter.MissionAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionBinding
import com.gsm.presentation.ui.mission.notification.MissionNotification
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

    val time: Long = 1000* 60 * 60
    var number: Int = 1

    override fun FragmentMissionBinding.onViewCreated() {
        getMissionType(DAY, 1)
        chipClickType()
        setAdapter()
        alarmManager()
        setTimer()
        observeGetMissionType()
        observeGetMission()
        observeErrorMessage()


    }


    private fun alarmManager() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MissionNotification::class.java)

        intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val alarmTimeAtUTC: Long = System.currentTimeMillis() + time


        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            Log.d("alarm", "alarmManager: 성공 ! ")
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(alarmTimeAtUTC, pendingIntent),
                pendingIntent
            )
        } else {
            Log.d("alarm", "alarmManager: 실패 ! ")
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeAtUTC,
                pendingIntent
            )
        }
    }


    private fun setTimer() {
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                Log.d("시간", "run: $time ")
                getMission()
            }
        }
        timer.schedule(task, 0L, time)

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
            setHasFixedSize(false)
        }
    }

    private fun getMission() {
        lifecycleScope.launch {

            viewModel.getMission(number++)

            Log.d("number", "getMission:  number : ${number}")
        }
    }

    private fun observeGetMission() {
        viewModel.missionData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                Log.d("remote", "observeGetMission: 데이터가 추가됨")
                remoteViewModel.insertMission(it)
            }
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