package com.bobrovskii.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.session.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val signInUseCase: SignInUseCase,
	private val router: AuthRouter,
) : ViewModel() {

	private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
	val state = _state.asStateFlow()

	private val _actions: Channel<AuthAction> = Channel(Channel.BUFFERED)
	val actions: Flow<AuthAction> = _actions.receiveAsFlow()

	fun onSignButtonPressed(token: String) {
		if (!validateToken(token)) {
			_state.value = AuthState.InvalidInput("Invalid token")
		} else {
			_state.value = AuthState.Loading
			viewModelScope.launch {
				try {
					signInUseCase(token)
					_state.value = AuthState.Idle
					_actions.send(AuthAction.RouteToMain)
				} catch (e: Exception) {
					_state.value = AuthState.Idle
					_actions.send(AuthAction.ShowError(e.message.toString()))
				}
			}
		}
	}

	private fun validateToken(token: String) =
		Regex("ghp_+([A-z0-9]{36}\$)").matches(token)

	fun routeToMain() {
		router.routeToMain()
	}

	fun onTextChanged() {
		if (_state.value is AuthState.InvalidInput) _state.value = AuthState.Idle
	}
}