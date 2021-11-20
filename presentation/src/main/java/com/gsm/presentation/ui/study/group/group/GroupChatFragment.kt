package com.gsm.presentation.ui.study.group.group

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseFragment
import com.gsm.presentation.databinding.FragmentStudyMeetingBinding
import com.gsm.presentation.adapter.GroupRecyclerAdapter
import com.gsm.presentation.util.extension.showVertical
import com.gsm.presentation.viewmodel.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroupChatFragment :
    BaseFragment<FragmentStudyMeetingBinding>(R.layout.fragment_study_meeting){

    private val viewModel: GroupViewModel by viewModels()
    private val groupAdapter: GroupRecyclerAdapter by lazy {
        GroupRecyclerAdapter()
    }

    override fun FragmentStudyMeetingBinding.onCreateView() {
        setHasOptionsMenu(true)

        with(viewModel) {
            getQuery("")
        }

    }

    override fun FragmentStudyMeetingBinding.onViewCreated() {
        initRecyclerView()
        with(viewModel) {

        }
    }

    private fun initRecyclerView() {
        binding.studyMeetingRecyclerView.adapter = groupAdapter
        binding.studyMeetingRecyclerView.showVertical(requireContext())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.menu_action_search).actionView as SearchView

        searchView.queryHint = getString(R.string.search_view_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getQuery(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.getQuery(newText)
                }

                return true
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_action_search ->
                true
            else -> super.onOptionsItemSelected(item)
        }

    }

}