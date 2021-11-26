package com.gsm.presentation.ui.study

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentCommunityBinding
import com.gsm.presentation.ui.study.chat.ChatListFragment
import com.gsm.presentation.ui.study.group.GroupListFragment
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {




    override fun FragmentCommunityBinding.onViewCreated() {
        initTabLayoutAndViewPager()
        fabClick()
         NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.groupListFragment -> binding.createGroupBtn.show()
                R.id.groupChatFragment -> binding.createGroupBtn.hide()

            }
        }
    }

    override fun FragmentCommunityBinding.onCreateView() {
        binding.fragmernt = this@CommunityFragment
    }

    private fun initTabLayoutAndViewPager() {
        binding.viewPager.adapter = FragmentAdapter(requireParentFragment())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->

            when (position) {
                0 -> tab.text = "그룹"
                1 -> tab.text = "chat"
            }

        }.attach()
    }



    class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> GroupListFragment()
                1 -> ChatListFragment()
                else -> GroupListFragment()
            }
        }

    }



    private fun fabClick() {
        binding.createGroupBtn.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_createGroupFragment)
        }
    }



}