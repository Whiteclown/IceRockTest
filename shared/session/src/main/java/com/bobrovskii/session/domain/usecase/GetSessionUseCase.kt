package com.bobrovskii.session.domain.usecase

import com.bobrovskii.session.data.storage.KeyValueStorage
import com.bobrovskii.session.domain.repository.LoginRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
	private val repository: LoginRepository
) {

	operator fun invoke(): KeyValueStorage = repository.get()
}