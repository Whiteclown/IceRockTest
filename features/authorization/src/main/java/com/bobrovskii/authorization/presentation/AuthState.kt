package com.bobrovskii.authorization.presentation

sealed interface AuthState {
	object Idle : AuthState
	object Loading : AuthState
	data class InvalidInput(val reason: String) : AuthState
}