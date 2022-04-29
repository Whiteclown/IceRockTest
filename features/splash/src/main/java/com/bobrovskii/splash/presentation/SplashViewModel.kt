package com.bobrovskii.splash.presentation

import androidx.lifecycle.ViewModel
import com.bobrovskii.session.domain.usecase.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
	private val router: SplashRouter,
	private val getSessionUseCase: GetSessionUseCase,
) : ViewModel() {

	fun routeFromSplash() {
		getSessionUseCase().authToken?.let {
			router.routeToRepositoriesListFromSplash()
		} ?: run {
			router.routeToAuthFromSplash()
		}
	}
}