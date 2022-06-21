package com.bobrovskii.detailinfo.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bobrovskii.detailinfo.R
import com.bobrovskii.detailinfo.databinding.FragmentDetailsBinding
import com.bobrovskii.detailinfo.presentation.DetailsState
import com.bobrovskii.detailinfo.presentation.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

	private var _binding: FragmentDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: DetailsViewModel by viewModels()

	private val ownerName: String
		get() = requireNotNull(requireArguments().getString(OWNER_NAME))

	private val repoName: String
		get() = requireNotNull(requireArguments().getString(REPO_NAME))

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentDetailsBinding.bind(view)
		initUIState()
		initListeners()
		viewModel.getRepo(ownerName, repoName)
	}

	private fun initListeners() {
		binding.logoutBtn.setOnClickListener {
			viewModel.logout()
		}
		binding.ivBack.setOnClickListener {
			viewModel.routeBack()
		}
		binding.mainNetworkErrorView.btnRefresh.root.setOnClickListener {
			viewModel.getRepo(ownerName, repoName)
		}
		binding.mainUndefinedErrorView.btnRefresh.root.setOnClickListener {
			viewModel.getRepo(ownerName, repoName)
		}
		binding.readmeNetworkErrorView.btnRefresh.root.setOnClickListener {
			viewModel.getRepoReadme()
		}
		binding.readmeUndefinedErrorView.btnRefresh.root.setOnClickListener {
			viewModel.getRepoReadme()
		}
	}

	private fun initUIState() {
		viewModel.state.onEach(::renderState).launchIn(viewModel.viewModelScope)
		binding.mainNetworkErrorView.btnRefresh.btnText.text = "RETRY"
		binding.mainUndefinedErrorView.btnRefresh.btnText.text = "REFRESH"
		binding.readmeNetworkErrorView.btnRefresh.btnText.text = "RETRY"
		binding.readmeUndefinedErrorView.btnRefresh.btnText.text = "REFRESH"
	}

	private fun renderState(state: DetailsState) {
		with(binding) {
			mainLoadingView.visibility = if (state is DetailsState.Loading) View.VISIBLE else View.GONE
			toolbarContainer.visibility = if (state is DetailsState.Loading) View.GONE else View.VISIBLE
			scrollView.visibility = if (state is DetailsState.Loaded) View.VISIBLE else View.GONE

			context?.let {
				if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loaded) Markwon.create(it)
					.setMarkdown(repoDescription, state.readmeState.markdown)
			}
			if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Empty) repoDescription.text = "No README.md"
			repoDescription.visibility = if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loading) View.GONE else View.VISIBLE
			readmeProgressBar.visibility =
				if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loading) View.VISIBLE else View.GONE
			readmeLoadingView.visibility = if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Error) View.GONE else View.VISIBLE

			titleRepo.text = if (state is DetailsState.Loaded) state.githubRepo.name else ""
			tvForksCount.text = if (state is DetailsState.Loaded) state.githubRepo.forks.toString() else ""
			tvStarsCount.text = if (state is DetailsState.Loaded) state.githubRepo.stargazersCount.toString() else ""
			tvWatchersCount.text = if (state is DetailsState.Loaded) state.githubRepo.watchersCount.toString() else ""
			licenseVal.text = if (state is DetailsState.Loaded) state.githubRepo.license ?: getString(R.string.missing_license) else ""
			weblink.text = if (state is DetailsState.Loaded) state.githubRepo.htmlUrl else ""

			binding.mainNetworkErrorView.root.visibility = if (state is DetailsState.Error && state.isNoConnectionError) View.VISIBLE else View.GONE

			binding.mainUndefinedErrorView.root.visibility = if (state is DetailsState.Error && !state.isNoConnectionError) View.VISIBLE else View.GONE

			binding.readmeNetworkErrorView.root.visibility = if (
				state is DetailsState.Loaded &&
				state.readmeState is DetailsState.ReadmeState.Error &&
				state.readmeState.isNoConnectionError
			) View.VISIBLE else View.GONE

			binding.readmeUndefinedErrorView.root.visibility = if (
				state is DetailsState.Loaded &&
				state.readmeState is DetailsState.ReadmeState.Error &&
				!state.readmeState.isNoConnectionError
			) View.VISIBLE else View.GONE
		}
	}

	companion object {

		private const val OWNER_NAME = "ownerName"
		private const val REPO_NAME = "repoName"

		fun createBundle(ownerName: String, repoName: String) =
			bundleOf(
				OWNER_NAME to ownerName,
				REPO_NAME to repoName,
			)
	}
}