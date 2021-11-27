package com.gsm.presentation.ui.study.chat

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.adapter.ChatListSAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentChatListBinding
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatListFragment : BaseFragment<FragmentChatListBinding>(R.layout.fragment_chat_list) {

    private val groupAdapter: ChatListSAdapter by lazy {
        ChatListSAdapter()
    }
    private val viewModel: GroupViewModel by activityViewModels()
    private fun initRecyclerView() {
        binding.partnerRecyclerView.showVertical(requireContext())
        binding.partnerRecyclerView.adapter = groupAdapter
    }

    override fun FragmentChatListBinding.onCreateView() {

    }

    override fun FragmentChatListBinding.onViewCreated() {
        initRecyclerView()
        with(viewModel) {
            lifecycleScope.launch {
                getChat("")
                    .collectLatest {
                        (groupAdapter).submitData(
                            it
                        )
                    }
            }
        }
    }


}