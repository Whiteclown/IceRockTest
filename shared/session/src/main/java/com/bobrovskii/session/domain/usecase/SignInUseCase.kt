package com.bobrovskii.session.domain.usecase

import com.bobrovskii.session.data.storage.KeyValueStorage
import com.bobrovskii.session.domain.repository.LoginRepository
import com.bobrovskii.session.domain.repository.SessionRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
	private val sessionRepository: SessionRepository,
	private val loginRepository: LoginRepository,
) {

	suspend operator fun invoke(token: String) {
		val userInfo = sessionRepository.signIn(token)
		loginRepository.save(KeyValueStorage(authToken = token, username = userInfo.username))
	}
}