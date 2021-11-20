package com.gsm.presentation.ui.study.group

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentCommunityBinding
import com.gsm.presentation.ui.study.StudyFragment
import com.gsm.presentation.ui.study.dialog.CreateGroupDialog
import com.gsm.presentation.ui.study.group.partner.PartnerFragment
import com.gsm.presentation.viewmodel.group.GroupViewModel
import com.gsm.presentation.viewmodel.sign.`in`.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {


    private val viewModel by viewModels<GroupViewModel>()
    private val signViewModel by activityViewModels<SignInViewModel>()


    override fun FragmentCommunityBinding.onViewCreated() {
        initTabLayoutAndViewPager()
    }

    override fun FragmentCommunityBinding.onCreateView() {
        binding.fragmernt=this@CommunityFragment
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

    fun createDialog(view: View){
        val dialog=CreateGroupDialog()
        dialog.show(childFragmentManager,"dialog")
    }

    class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StudyFragment()
                1 -> PartnerFragment()
                else -> StudyFragment()
            }
        }

    }


}