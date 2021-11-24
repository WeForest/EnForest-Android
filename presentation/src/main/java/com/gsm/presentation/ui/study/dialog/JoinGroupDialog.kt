package com.gsm.presentation.ui.study.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseDialogFragment
import com.gsm.presentation.databinding.JoinDialogBinding
import com.gsm.presentation.ui.splash.SplashActivity.Companion.deviceSizeX
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinGroupDialog :
    BaseDialogFragment<JoinDialogBinding>(R.layout.join_dialog) {
    var token = ""
    private val signViewModel by activityViewModels<SignInViewModel>()
    private val viewModel by viewModels<GroupViewModel>()
    private val args by navArgs<JoinGroupDialogArgs>()
    override fun JoinDialogBinding.onCreateView() {
        getToken()
    }

    override fun onResume() {
        super.onResume()
        dialogCorner()
        initDialog()

    }

    fun dialogCorner() {
        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createGroup(view)
    }

    fun createGroup(view: View) {
        binding.joinBtn.setOnClickListener {
            Log.d("TAG", "createGroup: ")
            lifecycleScope.launch {
                viewModel.joinGroup(
                    token,
                    args.groupId
                )
            }
        }
    }

    private fun initDialog() {
        //        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = deviceSizeX
        Log.d("로그", "acceptDialog : $deviceWidth")
        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun getToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }

    override fun JoinDialogBinding.onViewCreated() {
        with(viewModel) {
            success.observe(viewLifecycleOwner, EventObserver {

                if (it == true) {
                    Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()

                } else {
                    Toast.makeText(requireContext(), "실패패", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            })
        }
    }
}