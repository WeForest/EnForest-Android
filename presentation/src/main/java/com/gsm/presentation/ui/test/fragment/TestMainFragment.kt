package com.gsm.presentation.ui.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.gsm.presentation.R
import com.gsm.presentation.adapter.TestViewPagerAdapter
import com.gsm.presentation.databinding.FragmentTestMainBinding
import com.gsm.presentation.viewmodel.test.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestMainFragment : Fragment() {
    private val viewModel by activityViewModels<TestViewModel>()
    private lateinit var binding : FragmentTestMainBinding
    /* 여백, 너비에 대한 정의 */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_main, container, false)

        binding.fragment = this

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin) // dimen 파일 안에 크기를 정의해두었다.
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth) // dimen 파일이 없으면 생성해야함
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.testViewpager.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        binding.testViewpager.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지
        binding.testViewpager.adapter = TestViewPagerAdapter(levelList(),levelListText(),this,viewModel)
        binding.testViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    fun TestMOnclick(){

        //중급
        if (viewModel.wrongM.size >= 1) {
            if (findNavController().currentDestination?.id == R.id.testMainFragment) {
                findNavController().navigate(R.id.action_testMainFragment2_to_worngMFragment2)
            }
            else{
                findNavController().navigate(R.id.action_testMainFragment2_to_worngMFragment2)
            }
        }

        else
        {
            Toast.makeText(requireContext(), "우선 역량테스트(중급)를 진행 하신 후 다시오세요!", Toast.LENGTH_SHORT).show()
        }
    }

    fun TestOnclick(){
        if (viewModel.wrong.size >= 1) {
            if (findNavController().currentDestination?.id == R.id.testMainFragment) {
                findNavController().navigate(R.id.action_testMainFragment2_to_worngFragment2)
            }
            else{
                findNavController().navigate(R.id.action_testMainFragment2_to_worngFragment2)
            }
        }

        else
        {
            Toast.makeText(requireContext(), "우선 역량테스트(초급)를 진행 하신 후 다시오세요!", Toast.LENGTH_SHORT).show()
        }
    }

    fun TestHOnclick(){
        if (viewModel.wrongH.size >= 1) {
            if (findNavController().currentDestination?.id == R.id.testMainFragment) {
                findNavController().navigate(R.id.action_testMainFragment2_to_worngHFragment2)
            }
            else{
                findNavController().navigate(R.id.action_testMainFragment2_to_worngHFragment2)
            }
        }

        else
        {
            Toast.makeText(requireContext(), "우선 역량테스트(고급)를 진행 하신 후 다시오세요!", Toast.LENGTH_SHORT).show()
        }
    }

    fun toNavigationWrong() {
        binding.foldingCell.toggle(false)
    }

    // 뷰 페이저에 들어갈 아이템
    private fun levelList(): ArrayList<String> {
        return arrayListOf<String>("#B6D1D4", "#EAA18A","#F55354")
    }

    private fun levelListText(): ArrayList<Int> {
        return arrayListOf<Int>(R.string.basic_test, R.string.middle_test, R.string.hard_test)
    }
}