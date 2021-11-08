package com.kdn.presentation.ui.mission

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.kdn.presentation.R
import com.kdn.presentation.base.BaseBottomSheetDialogFragment
import com.kdn.presentation.base.BaseFragment
import com.kdn.presentation.databinding.FragmentMissionBinding
import com.kdn.presentation.viewmodel.mission.MissionViewModel

class MissionFragment : BaseFragment<FragmentMissionBinding>(R.layout.fragment_mission) {
    private val viewModel: MissionViewModel by viewModels<MissionViewModel>()
    override fun FragmentMissionBinding.onCreateView() {
        binding.fragment = this@MissionFragment
    }

    override fun FragmentMissionBinding.onViewCreated() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MissionNotification::class.java)

        intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val msUntilTriggerHour: Long = 10000
        val alarmTimeAtUTC: Long = System.currentTimeMillis() + msUntilTriggerHour


        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(alarmTimeAtUTC, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeAtUTC,
                pendingIntent
            )
        }
    }

    fun writeLayoutClick(view: View?) {
        view?.findNavController()?.navigate(R.id.action_fragmentMission_to_bottomSheetMissionDialog)
    }
}