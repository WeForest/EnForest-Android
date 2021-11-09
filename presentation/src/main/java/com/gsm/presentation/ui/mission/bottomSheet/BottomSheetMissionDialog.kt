package com.gsm.presentation.ui.mission.bottomSheet

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseBottomSheetDialogFragment
import com.gsm.presentation.databinding.BottomSheetDialogWriteMissionBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.util.extension.TimeConverter
import com.gsm.presentation.viewmodel.mission.MissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class BottomSheetMissionDialog() :
    BaseBottomSheetDialogFragment<BottomSheetDialogWriteMissionBinding>(R.layout.bottom_sheet_dialog_write_mission) {

    private val viewModel: MissionViewModel by viewModels<MissionViewModel>()

    private val timeConverter: TimeConverter by lazy { TimeConverter() }
    override fun BottomSheetDialogWriteMissionBinding.onCreateView() {

    }

    var chipText = "daily"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun BottomSheetDialogWriteMissionBinding.onViewCreated() {
        chipClickType()
        Log.d("TAG", "onViewCreated: ${chipText()}")
        with(viewModel) {
            // 미션생성하기
            binding.finishBtn.setOnClickListener {
                lifecycleScope.launch {
                    if (binding.missionWriteContentEditText.text.toString().trim()
                            .isNotEmpty() && binding.missionWriteEditText.text.toString()
                            .trim()
                            .isNotEmpty()
                    ) {
                        addMission(
                            binding.missionWriteEditText.text.toString(),
                            binding.missionWriteContentEditText.text.toString(),
                            chipText(),
                            chipText
                        )
                    } else {
                        Toast.makeText(requireContext(), "빈칸을 입력해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            success.observe(this@BottomSheetMissionDialog,EventObserver {

                if (it) {
                    Toast.makeText(requireContext(), "성공했습니다 !.", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                } else {
                    Toast.makeText(requireContext(), "실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun chipClickType() {

        binding.chipType.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            chipText = selectedMealType
        }
        Log.i("TAG", "chipClickType: ${chipText}")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun chipText(): Int {
        return when (chipText) {

            "daily" -> 1
            "weekly" -> 7
            "monthly" -> timeConverter.addMonth()
            else -> 0
        }
    }


}


