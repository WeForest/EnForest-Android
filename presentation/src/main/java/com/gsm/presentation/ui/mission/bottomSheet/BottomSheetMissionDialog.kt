package com.gsm.presentation.ui.mission.bottomSheet

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseBottomSheetDialogFragment
import com.gsm.presentation.databinding.BottomSheetDialogWriteMissionBinding
import com.gsm.presentation.ui.mission.MissionFragment.Companion.DAY
import com.gsm.presentation.ui.mission.MissionFragment.Companion.MONTH
import com.gsm.presentation.ui.mission.MissionFragment.Companion.WEEK
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

    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }

    var chipText = "daily"
    var chipLevelText = "low"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun BottomSheetDialogWriteMissionBinding.onViewCreated() {
        view?.setBackgroundResource(R.drawable.mission_background)

        chipClickType()
        chipLevelClickType()
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
                            chipLevelText, // low
                            binding.missionWriteEditText.text.toString(), // 제목
                            binding.missionWriteContentEditText.text.toString(), //내용
                            chipText(), // 날짜
                            chipText //타입
                        )
                    } else {
                        Toast.makeText(requireContext(), "빈칸을 입력해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            addMissionData.observe(viewLifecycleOwner) {
                val action =
                    BottomSheetMissionDialogDirections.actionBottomSheetMissionDialogToMissionFragment(
                        true
                    )
                findNavController().navigate(action)
            }
            success.observe(this@BottomSheetMissionDialog, EventObserver {

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

    private fun chipLevelClickType() {

        binding.chipLevelType.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            chipLevelText = selectedMealType
        }
        Log.i("TAG", "chipClickType: ${chipLevelText}")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun chipText(): Int {
        return when (chipText) {

            DAY -> 1
            WEEK -> 7
            MONTH -> timeConverter.addMonth()
            else -> 0
        }
    }


}


