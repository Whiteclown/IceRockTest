package com.bobrovskii.repositorieslist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bobrovskii.repositorieslist.R
import com.bobrovskii.repositorieslist.databinding.FragmentRepositoriesListBinding
import com.bobrovskii.repositorieslist.presentation.RepositoriesListState
import com.bobrovskii.repositorieslist.presentation.RepositoriesListViewModel
import com.bobrovskii.repositorieslist.ui.adapter.RepoAdapter
import com.bobrovskii.session.domain.entity.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

	private var _binding: FragmentRepositoriesListBinding? = null
	private val binding get() = _binding!!

	private val viewModel: RepositoriesListViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentRepositoriesListBinding.bind(view)
		initListeners()
		initUIState()
		viewModel.getRepositories()
	}

	private fun initListeners() {
		binding.logoutBtn.setOnClickListener {
			viewModel.logout()
		}
	}

	private fun initUIState() {
		viewModel.state.onEach(::renderState).launchIn(viewModel.viewModelScope)
	}

	private fun renderState(state: RepositoriesListState) {
		binding.toolbarContainer.visibility = if (state is RepositoriesListState.Loading) View.GONE else View.VISIBLE
		binding.loadingView.visibility = if (state is RepositoriesListState.Loading) View.VISIBLE else View.GONE
		if (state is RepositoriesListState.Loaded) initRV(state.repos)
	}

	private fun initRV(repos: List<Repo>) {
		binding.recyclerView.adapter = RepoAdapter(
			repos = repos,
			onItemClick = {
				viewModel.routeToDetails(it)
			},
		)
	}
}