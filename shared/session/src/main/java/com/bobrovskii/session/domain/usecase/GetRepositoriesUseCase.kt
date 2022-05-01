package com.bobrovskii.session.domain.usecase

import com.bobrovskii.session.domain.repository.SessionRepository
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
	private val repository: SessionRepository,
) {

	suspend operator fun invoke() =
		repository.getRepositories()
}