package com.bobrovskii.detailinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.core.Base64Decoder
import com.bobrovskii.core.NoNetworkConnectionException
import com.bobrovskii.session.domain.usecase.ClearSessionUseCase
import com.bobrovskii.session.domain.usecase.GetRepositoryReadmeUseCase
import com.bobrovskii.session.domain.usecase.GetRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
	private val getRepositoryUseCase: GetRepositoryUseCase,
	private val getRepositoryReadmeUseCase: GetRepositoryReadmeUseCase,
	private val clearSessionUseCase: ClearSessionUseCase,
	private val router: DetailsRouter,
) : ViewModel() {

	private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
	val state = _state.asStateFlow()

	fun getRepo(ownerName: String, repositoryName: String) {
		viewModelScope.launch {
			_state.value = DetailsState.Loading
			try {
				val repo = getRepositoryUseCase(ownerName, repositoryName)
				_state.value = DetailsState.Loaded(
					githubRepo = repo,
					readmeState = DetailsState.ReadmeState.Loading,
				)
				getRepoReadme()
			} catch (e: Exception) {
				when (e) {
					is NoNetworkConnectionException -> _state.value = DetailsState.Error(e.message, true)
					else                            -> _state.value = DetailsState.Error(e.message.toString(), false)
				}
			}
		}
	}

	fun getRepoReadme() {
		viewModelScope.launch {
			val content = _state.value as DetailsState.Loaded
			try {
				val readme = getRepositoryReadmeUseCase(ownerName = content.githubRepo.owner, repositoryName = content.githubRepo.name)
				_state.value = DetailsState.Loaded(
					githubRepo = content.githubRepo,
					readmeState = DetailsState.ReadmeState.Loaded(
						markdown = Base64Decoder.decode(readme.content)
					),
				)
			} catch (e: Exception) {
				when (e) {
					is NoNetworkConnectionException -> _state.value = DetailsState.Loaded(
						githubRepo = content.githubRepo,
						readmeState = DetailsState.ReadmeState.Error(e.message, true),
					)

					is HttpException                -> {
						if (e.code() == 404) {
							_state.value = DetailsState.Loaded(
								githubRepo = content.githubRepo,
								readmeState = DetailsState.ReadmeState.Empty,
							)
						}
					}

					else                            -> _state.value = DetailsState.Loaded(
						githubRepo = content.githubRepo,
						readmeState = DetailsState.ReadmeState.Error(e.message.toString(), false),
					)
				}
			}
		}
	}

	fun routeBack() {
		router.routeBack()
	}

	fun logout() {
		clearSessionUseCase()
		router.routeToAuthFromDetails()
	}
}