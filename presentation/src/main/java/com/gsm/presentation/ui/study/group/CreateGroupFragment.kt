package com.gsm.presentation.ui.study.group

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentCreateGroupBinding
import com.gsm.presentation.util.DataState
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGroupFragment :
    BaseFragment<FragmentCreateGroupBinding>(R.layout.fragment_create_group) {


    private val viewModel: GroupViewModel by viewModels()
    private val signViewModel: SignInViewModel by viewModels()
    var token=""
    override fun FragmentCreateGroupBinding.onCreateView() {
        observeToken()

    }

    override fun FragmentCreateGroupBinding.onViewCreated() {
        with(binding) {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            createGroupBtn.setOnClickListener {
                if (textNullTest()) {
                    lifecycleScope.launch {
                        viewModel.createGroup(
                            token,
                            binding.groupNameEdit.text.toString(),
                            binding.groupContentEdit.text.toString(),
                            binding.groupTagEdit.text.toString()
                        )
                    }
                } else {
                    Toast.makeText(requireContext(), "빈칸을 빠짐없이 채워주세요", Toast.LENGTH_SHORT).show()
                }
            }
            observe()

        }
    }


    private fun observe() = with(viewModel) {
        storeState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    findNavController().navigate(R.id.action_createGroupFragment_to_communityFragment)
                    binding.loadingProgress.visibility= View.GONE


                }
                is DataState.Failure -> {
                    binding.loadingProgress.visibility= View.GONE

                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    binding.loadingProgress.visibility= View.VISIBLE
                }
            }
        }
    }


    private fun textNullTest(): Boolean {
        return !(TextUtils.isEmpty(binding.groupContentEdit.text.toString())) && !(TextUtils.isEmpty(
            binding.groupTagEdit.text.toString()
        )) && !(TextUtils.isEmpty(binding.groupNameEdit.text.toString()))

    }

    private fun observeToken(){
        signViewModel.readToken.asLiveData().observe(viewLifecycleOwner){
            token=it.token
        }

    }


}