package com.bobrovskii.detailinfo.presentation

import com.bobrovskii.session.domain.entity.RepoDetails

sealed interface DetailsState {

	object Loading : DetailsState

	data class Error(val error: String, val isNoConnectionError: Boolean) : DetailsState

	data class Loaded(
		val githubRepo: RepoDetails,
		val readmeState: ReadmeState,
	) : DetailsState

	sealed interface ReadmeState {

		object Loading : ReadmeState

		object Empty : ReadmeState

		data class Error(val error: String, val isNoConnectionError: Boolean) : ReadmeState

		data class Loaded(val markdown: String) : ReadmeState
	}
}