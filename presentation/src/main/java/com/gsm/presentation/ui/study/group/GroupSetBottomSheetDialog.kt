package com.gsm.presentation.ui.study.group

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseBottomSheetDialogFragment
import com.gsm.presentation.databinding.FragmentGroupListBottomSheetBinding
import com.gsm.presentation.util.EventObserver
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.profile.ProfileViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroupSetBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentGroupListBottomSheetBinding>(R.layout.fragment_group_list_bottom_sheet) {

    private val viewModel: GroupViewModel by viewModels()
    private val signViewModel: SignInViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    var token = ""
    private val args by navArgs<GroupSetBottomSheetDialogArgs>()

    override fun FragmentGroupListBottomSheetBinding.onCreateView() {

        observeToken()
    }

    override fun FragmentGroupListBottomSheetBinding.onViewCreated() {
        with(binding) {
            followLayout.setOnClickListener {
                lifecycleScope.launch {
                    profileViewModel.postFollow(token, args.nickname)
                }
            }
            enterLayout.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.joinGroup(token, args.groupId)
                }
            }
            unFollowLayout.setOnClickListener {
                lifecycleScope.launch {
                    profileViewModel.unPostFollow(token, args.nickname)
                }
            }
            observeSuccess()

        }
    }

    private fun observeSuccess(){
        profileViewModel.isSuccess.observe(viewLifecycleOwner,EventObserver{
            if(it){
                Toast.makeText(requireContext(),"성공",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"실패",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.success.observe(viewLifecycleOwner,EventObserver{
            if(it == true){
                Toast.makeText(requireContext(),"성공",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observeToken() {
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner) {
            token = it.token
        }
    }
}