package com.bobrovskii.session.domain.usecase

import com.bobrovskii.session.domain.repository.LoginRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
	private val repository: LoginRepository,
) {

	operator fun invoke() = repository.clear()
}