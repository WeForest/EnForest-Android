package com.gsm.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gsm.presentation.ui.mission.detail.EasyFragment
import com.gsm.presentation.ui.mission.detail.HardFragment
import com.gsm.presentation.ui.mission.detail.NormalFragment

class DetailPagerAdapter(fragmentActivity: Fragment) :
    FragmentStateAdapter(fragmentActivity) {

    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> (EasyFragment())
            1 ->(NormalFragment())
            2 -> (HardFragment())
            else -> (EasyFragment())
        }
    }





}
