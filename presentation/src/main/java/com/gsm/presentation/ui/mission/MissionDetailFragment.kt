package com.gsm.presentation.ui.mission

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.presentation.R
import com.gsm.presentation.adapter.DetailPagerAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionDetailBinding
import com.gsm.presentation.ui.mission.detail.EasyFragment
import com.gsm.presentation.ui.mission.detail.HardFragment
import com.gsm.presentation.ui.mission.detail.NormalFragment

class MissionDetailFragment :
    BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {
    val pagerAdapter = DetailPagerAdapter(requireActivity())


    override fun FragmentMissionDetailBinding.onViewCreated() {
        setUpViewPager()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerAdapter.removeFragment()
    }


    private fun setUpViewPager() {

        // 3개의 Fragment Add
        pagerAdapter.addFragment(EasyFragment())
        pagerAdapter.addFragment(NormalFragment())
        pagerAdapter.addFragment(HardFragment())
        // Adapter
        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position + 1}")
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "쉬움"
                1 -> tab.text = "보통"
                2 -> tab.text = "어려움"
                else -> ""
            }
        }
    }


}


