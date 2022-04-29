package com.bobrovskii.authorization.presentation

sealed interface AuthAction {
	data class ShowError(val message: String) : AuthAction
	object RouteToMain : AuthAction
}