package com.bobrovskii.repositorieslist.presentation

import com.bobrovskii.session.domain.entity.Repo

interface RepositoriesListState {
	object Loading : RepositoriesListState
	data class Loaded(val repos: List<Repo>) : RepositoriesListState
	data class Error(val error: String) : RepositoriesListState
	object Empty : RepositoriesListState
}