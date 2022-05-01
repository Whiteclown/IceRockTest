package com.bobrovskii.detailinfo.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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

	companion object {

		private const val OWNER_NAME = "ownerName"
		private const val REPO_NAME = "repoName"

		fun createBundle(ownerName: String, repoName: String) =
			bundleOf(
				OWNER_NAME to ownerName,
				REPO_NAME to repoName,
			)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentDetailsBinding.bind(view)
		initObservers()
		initListeners()
		viewModel.getRepo(ownerName, repoName)
	}

	private fun initListeners() {
		binding.logoutBtn.setOnClickListener {
			viewModel.logout()
		}
	}

	private fun initObservers() {
		viewModel.state.onEach(::renderState).launchIn(viewModel.viewModelScope)
	}

	private fun renderState(state: DetailsState) {
		with(binding) {
			mainLoadingView.visibility = if (state is DetailsState.Loading) View.VISIBLE else View.GONE
			toolbarContainer.visibility = if (state is DetailsState.Loading) View.GONE else View.VISIBLE
			scrollView.visibility = if (state is DetailsState.Loading) View.GONE else View.VISIBLE

			repoDescription.visibility = if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loading) View.GONE else View.VISIBLE
			readmeProgressBar.visibility =
				if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loading) View.VISIBLE else View.GONE

			titleRepo.text = if (state is DetailsState.Loaded) state.githubRepo.name else ""
			forks.text = if (state is DetailsState.Loaded) getString(R.string.forks_text, state.githubRepo.forks) else ""
			stars.text = if (state is DetailsState.Loaded) getString(R.string.stars_text, state.githubRepo.stargazersCount) else ""
			watchers.text = if (state is DetailsState.Loaded) getString(R.string.watchers_text, state.githubRepo.watchersCount) else ""
			licenseVal.text = if (state is DetailsState.Loaded) state.githubRepo.license ?: getString(R.string.missing_license) else ""
			weblink.text = if (state is DetailsState.Loaded) state.githubRepo.htmlUrl else ""

			context?.let {
				if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Loaded) Markwon.create(it)
					.setMarkdown(repoDescription, state.readmeState.markdown)
			}

			if (state is DetailsState.Loaded && state.readmeState is DetailsState.ReadmeState.Error) {
				context?.let {
					AlertDialog
						.Builder(it)
						.setTitle(getString(R.string.readme_error_dialog_title))
						.setMessage(state.readmeState.error)
						.setPositiveButton(getString(R.string.error_dialog_positive_btn_text)) { _, _ ->
							viewModel.getRepoReadme()
						}
						.setNegativeButton(getString(R.string.readme_error_dialog_negative_btn_text)) { _, _ ->
						}
						.show()
				}
			}
			if (state is DetailsState.Error) {
				context?.let {
					AlertDialog
						.Builder(it)
						.setTitle(getString(R.string.repo_error_dialog_title))
						.setMessage(state.error)
						.setPositiveButton(getString(R.string.error_dialog_positive_btn_text)) { _, _ ->
							viewModel.getRepo(ownerName, repoName)
						}
						.setNegativeButton(getString(R.string.repo_error_dialog_negative_btn_text)) { _, _ ->
							viewModel.routeBack()
						}
						.show()
				}
			}
		}
	}
}