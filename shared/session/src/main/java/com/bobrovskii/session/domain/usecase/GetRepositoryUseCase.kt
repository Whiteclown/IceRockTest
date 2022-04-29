package com.bobrovskii.session.domain.usecase

import com.bobrovskii.session.domain.repository.SessionRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(
	private val repository: SessionRepository,
) {

	suspend operator fun invoke(ownerName: String, repositoryName: String) =
		repository.getRepository(ownerName, repositoryName)
}