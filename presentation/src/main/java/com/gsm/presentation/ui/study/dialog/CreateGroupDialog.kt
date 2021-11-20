package com.gsm.presentation.ui.study.dialog

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseDialogFragment
import com.gsm.presentation.databinding.FragmentDialogCreateBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class CreateGroupDialog :
    BaseDialogFragment<FragmentDialogCreateBinding>(R.layout.fragment_dialog_create) {
    var token=""
    private val signViewModel by activityViewModels<SignInViewModel>()
    private val viewModel by viewModels<GroupViewModel>()

    override fun FragmentDialogCreateBinding.onCreateView() {
        binding.fragment=this@CreateGroupDialog
        getToken()
    }

    fun createGroup(view: View) {
        Log.d("TAG", "createGroup: ")
        lifecycleScope.launch {
            viewModel.createGroup(
               token,
                binding.titleEditTxt.text.toString(),
                binding.contentEditTxt.text.toString(),
                binding.tagEditTxt.text.toString()
            )
        }
    }

    private fun getToken(){
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner){
            token=it.token
        }
    }

    override fun FragmentDialogCreateBinding.onViewCreated() {
        with(viewModel){
            success.observe(viewLifecycleOwner,EventObserver{

                if(it == true){
                    Toast.makeText(requireContext(),"성공",Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()

                }else{
                    Toast.makeText(requireContext(),"실패패",Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            })
        }
    }
}