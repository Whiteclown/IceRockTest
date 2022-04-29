package com.bobrovskii.session.domain.repository

import com.bobrovskii.session.data.storage.KeyValueStorage

interface LoginRepository {

	fun save(storage: KeyValueStorage)

	fun get(): KeyValueStorage

	fun clear()
}