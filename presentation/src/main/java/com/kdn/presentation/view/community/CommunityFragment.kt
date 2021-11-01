package com.kdn.presentation.view.community

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gsm.presentation.R
import com.gsm.presentation.databinding.FragmentCommunityBinding
import com.kdn.presentation.base.BaseFragment

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun init() {

    }

    private fun initNavController(){
        navController = navHostFragment.navController
    }

    private fun initTabLayoutAndViewPager(){

    }

    class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 ->
            }
        }

    }
}