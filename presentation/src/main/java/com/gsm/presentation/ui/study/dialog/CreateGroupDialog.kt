package com.gsm.presentation.ui.study.dialog

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseDialogFragment
import com.gsm.presentation.databinding.FragmentDialogCreateBinding
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CreateGroupDialog :
    BaseDialogFragment<FragmentDialogCreateBinding>(R.layout.fragment_dialog_create) {
    private val signViewModel by activityViewModels<SignInViewModel>()
    private val viewModel by viewModels<GroupViewModel>()


    fun createGroup(view: View) {
        lifecycleScope.launch {
            viewModel.createGroup(
                signViewModel.tokenValue.toString(),
                binding.titleEditTxt.toString(),
                binding.contentTxt.text.toString(),
                binding.tagEditTxt.toString()
            )
        }

    }

}