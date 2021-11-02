package com.kdn.presentation.view.community

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentCommunityBinding
import com.kdn.presentation.base.BaseFragment
import com.kdn.presentation.view.community.partner.PartnerFragment
import com.kdn.presentation.view.community.studymeeting.StudyMeetingFragment

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun init() {
        initTabLayoutAndViewPager()
    }

    private fun initTabLayoutAndViewPager() {
        binding.viewPager.adapter = FragmentAdapter(requireParentFragment())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->


                when (position) {
                    0 -> tab.text = "모임"
                    1 -> tab.text = "파트너"
                }

        }.attach()
    }

    class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StudyMeetingFragment()
                1 -> PartnerFragment()
                else -> StudyMeetingFragment()
            }
        }

    }
}