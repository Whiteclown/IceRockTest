package com.bobrovskii.icerocktest

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bobrovskii.icerocktest.navigation.Navigator
import com.bobrovskii.session.domain.usecase.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val navigator: Navigator,
) : ViewModel() {

	fun bindNavController(navController: NavController) {
		navigator.bind(navController)
	}

	fun unbindNavController() {
		navigator.unbind()
	}
}