package com.bobrovskii.repositorieslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.core.NoNetworkConnectionException
import com.bobrovskii.session.domain.entity.Repo
import com.bobrovskii.session.domain.usecase.ClearSessionUseCase
import com.bobrovskii.session.domain.usecase.GetRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel @Inject constructor(
	private val getRepositoriesUseCase: GetRepositoriesUseCase,
	private val clearSessionUseCase: ClearSessionUseCase,
	private val router: RepositoriesListRouter,
) : ViewModel() {

	private val _state = MutableStateFlow<RepositoriesListState>(RepositoriesListState.Loading)
	val state = _state.asStateFlow()

	fun getRepositories() {
		viewModelScope.launch {
			_state.value = RepositoriesListState.Loading
			try {
				val repos = getRepositoriesUseCase()
				if (repos.isEmpty()) {
					_state.value = RepositoriesListState.Empty
				} else {
					_state.value = RepositoriesListState.Loaded(repos)
				}
			} catch (e: Exception) {
				_state.value = when (e) {
					is NoNetworkConnectionException -> RepositoriesListState.Error(e.message, true)
					else                            -> RepositoriesListState.Error(e.message.toString(), false)
				}
			}
		}
	}

	fun routeToDetails(repo: Repo) {
		router.routeToDetails(repo.owner, repo.name)
	}

	fun logout() {
		clearSessionUseCase()
		router.routeToAuthFromRepositories()
	}
}