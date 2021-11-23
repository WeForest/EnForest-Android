package com.gsm.presentation.ui.mission

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.presentation.R
import com.gsm.presentation.adapter.DetailPagerAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentMissionDetailBinding
import com.gsm.presentation.viewmodel.mission.MissionViewModel

class MissionDetailFragment :
    BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {
    private val pagerAdapter: DetailPagerAdapter by lazy { DetailPagerAdapter(requireParentFragment()) }

    private val missionViewModel: MissionViewModel by activityViewModels()
    private val args by navArgs<MissionDetailFragmentArgs>()
    override fun FragmentMissionDetailBinding.onViewCreated() {
        setUpViewPager()
        setHasOptionsMenu(false)
        missionViewModel.getType(args.type.toString())

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    private fun setUpViewPager() {

        // 3개의 Fragment Add

        // Adapter
        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("ViewPagerFragment", "Page ${position + 1}")
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "쉬움"
                1 -> tab.text = "보통"
                2 -> tab.text = "어려움"
                else -> ""
            }
        }.attach()
    }


}


