package com.bobrovskii.repositorieslist.presentation

interface RepositoriesListRouter {

	fun routeToDetails(ownerName: String, repoName: String)

	fun routeToAuthFromRepositories()
}