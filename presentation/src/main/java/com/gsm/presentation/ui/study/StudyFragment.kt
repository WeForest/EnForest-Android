package com.gsm.presentation.ui.study

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.adapter.GroupRecyclerAdapter
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentStudyBinding
import com.gsm.presentation.ui.main.MainActivity
import com.gsm.presentation.ui.study.group.CommunityFragment
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 그룹 리스트
@AndroidEntryPoint
class StudyFragment : BaseFragment<FragmentStudyBinding>(R.layout.fragment_study) {
    private val viewModel: GroupViewModel by viewModels()
    private val groupAdapter: GroupRecyclerAdapter by lazy {
        GroupRecyclerAdapter(this)
    }

    override fun FragmentStudyBinding.onCreateView() {
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.show()


    }

    override fun FragmentStudyBinding.onViewCreated() {
        initRecyclerView()
        with(viewModel) {
            lifecycleScope.launch {
                getQuery("")
                    .collectLatest {
                        (groupAdapter).submitData(
                            it
                        )
                    }
            }
        }
    }

    private fun initRecyclerView() {
        binding.studyRecycler.adapter = groupAdapter
        binding.studyRecycler.showVertical(requireContext())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.menu_action_search).actionView as SearchView

        searchView.queryHint = getString(R.string.search_view_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                lifecycleScope.launch {
                    viewModel.getQuery(query).collectLatest {
                        (groupAdapter).submitData(
                            it
                        )
                    }
                }

                if (query?.isEmpty() == true) {
                    binding.studyRecycler.scrollToPosition(0)
                }
                searchView.onActionViewCollapsed()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    Log.d("TAG", "onQueryTextChange: ")
                    lifecycleScope.launch {
                        viewModel.getQuery(newText).collectLatest {
                            (groupAdapter).submitData(
                                it
                            )
                        }
                    }
                }

                return true
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_action_search -> {
                (childFragmentManager.findFragmentById(R.id.communityFragment) as? CommunityFragment)?.binding?.apply {
                    tabLayout.visibility = View.INVISIBLE
                    createGroupBtn.visibility = View.INVISIBLE
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}