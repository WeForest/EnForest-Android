package com.gsm.presentation.ui.study.group

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gsm.data.entity.group.response.SearchGroupResponseItem
import com.gsm.presentation.R
import com.gsm.presentation.adapter.GroupListAdapter
import com.gsm.presentation.adapter.RecyclerViewItemClickListener
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentGroupListBinding
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 그룹 리스트
@AndroidEntryPoint
class GroupListFragment : BaseFragment<FragmentGroupListBinding>(R.layout.fragment_group_list),RecyclerViewItemClickListener<SearchGroupResponseItem> {
    private val viewModel: GroupViewModel by viewModels()
    private val groupAdapter: GroupListAdapter by lazy {
        GroupListAdapter(this)
    }

    override fun FragmentGroupListBinding.onCreateView() {
        searchSetting()

    }

    override fun FragmentGroupListBinding.onViewCreated() {
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


    fun searchSetting() {
        binding.searchView.queryHint = getString(R.string.search_view_hint)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                binding.searchView.onActionViewCollapsed()

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

    override fun onclick(data: SearchGroupResponseItem) {

    }


}