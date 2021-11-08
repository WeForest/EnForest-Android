package com.gsm.presentation.ui.mission

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.presentation.adapter.MissionAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import com.kdn.presentation.R
import com.kdn.presentation.databinding.FragmentMissionBinding

/*
todo : 하루에 하나씩 미션이 울려 저장을 해야함 내부 db 사용 헤애힘 !!
 */
class MissionFragment : BaseFragment<FragmentMissionBinding>(R.layout.fragment_mission) {
    private val missionAdapter: MissionAdapter by lazy { MissionAdapter() }
    private val viewModel: MissionViewModel by viewModels<MissionViewModel>()
    override fun FragmentMissionBinding.onCreateView() {
        binding.fragment = this@MissionFragment
    }

    override fun FragmentMissionBinding.onViewCreated() {
        setAdapter()
        alarmManager()
    }


    private fun alarmManager() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MissionNotification::class.java)

        intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val msUntilTriggerHour: Long = 10000
        val alarmTimeAtUTC: Long = System.currentTimeMillis() + msUntilTriggerHour


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

    private fun setAdapter() {
        binding.recyclerView.apply {

            adapter = missionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
        }
    }

    fun writeLayoutClick(view: View?) {
        view?.findNavController()?.navigate(R.id.action_fragmentMission_to_bottomSheetMissionDialog)
    }
}