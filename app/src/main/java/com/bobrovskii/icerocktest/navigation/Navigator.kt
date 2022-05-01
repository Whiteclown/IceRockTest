package com.bobrovskii.icerocktest.navigation

import androidx.navigation.NavController
import com.bobrovskii.authorization.presentation.AuthRouter
import com.bobrovskii.detailinfo.presentation.DetailsRouter
import com.bobrovskii.detailinfo.ui.DetailsFragment
import com.bobrovskii.icerocktest.R
import com.bobrovskii.repositorieslist.presentation.RepositoriesListRouter
import com.bobrovskii.splash.presentation.SplashRouter

class Navigator : AuthRouter, RepositoriesListRouter, DetailsRouter, SplashRouter {

	private var navController: NavController? = null

	fun bind(navController: NavController) {
		this.navController = navController
	}

	fun unbind() {
		navController = null
	}

	fun setMain() {
		navController?.navigate(R.id.repositoriesListFragment)
	}

	override fun routeToMain() {
		navController?.navigate(R.id.action_authFragment_to_repositoriesListFragment)
	}

	override fun routeToDetails(ownerName: String, repoName: String) {
		navController?.navigate(
			R.id.action_repositoriesListFragment_to_detailsFragment,
			DetailsFragment.createBundle(ownerName, repoName),
		)
	}

	override fun routeToAuthFromRepositories() {
		navController?.navigate(R.id.action_repositoriesListFragment_to_authFragment)
	}

	override fun routeToAuthFromDetails() {
		navController?.navigate(R.id.action_detailsFragment_to_authFragment)
	}

	override fun routeBack() {
		navController?.popBackStack()
	}

	override fun routeToAuthFromSplash() {
		navController?.navigate(R.id.action_splashFragment_to_authFragment)
	}

	override fun routeToRepositoriesListFromSplash() {
		navController?.navigate(R.id.action_splashFragment_to_repositoriesListFragment)
	}
}