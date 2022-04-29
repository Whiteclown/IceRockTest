package com.bobrovskii.session.data.repository

import android.content.Context
import com.bobrovskii.session.data.storage.KeyValueStorage
import com.bobrovskii.session.domain.repository.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context,
) : LoginRepository {

	private companion object {

		const val SESSION_PREFERENCES = "sessionPreferences"
		const val AUTH_TOKEN = "authToken"
		const val USERNAME = "userName"
	}

	private val sessionPreferences by lazy {
		context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)
	}

	override fun save(storage: KeyValueStorage) {
		sessionPreferences
			.edit()
			.putString(AUTH_TOKEN, storage.authToken)
			.putString(USERNAME, storage.username)
			.apply()
	}

	override fun get(): KeyValueStorage {
		return KeyValueStorage(
			sessionPreferences.getString(AUTH_TOKEN, null),
			sessionPreferences.getString(USERNAME, null),
		)
	}

	override fun clear() {
		sessionPreferences
			.edit()
			.clear()
			.apply()
	}
}