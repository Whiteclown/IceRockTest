package com.bobrovskii.session.data.repository

import com.bobrovskii.session.data.api.SessionApi
import com.bobrovskii.session.data.mapper.toEntity
import com.bobrovskii.session.domain.entity.Readme
import com.bobrovskii.session.domain.entity.Repo
import com.bobrovskii.session.domain.entity.RepoDetails
import com.bobrovskii.session.domain.entity.UserInfo
import com.bobrovskii.session.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
	private val api: SessionApi,
) : SessionRepository {

	override suspend fun getRepositories(): List<Repo> =
		api.getRepositories().map { it.toEntity() }

	override suspend fun getRepository(ownerName: String, repositoryName: String): RepoDetails =
		api.getRepository(ownerName, repositoryName).toEntity()

	override suspend fun getRepositoryReadme(ownerName: String, repositoryName: String): Readme =
		api.getRepositoryReadme(ownerName = ownerName, repositoryName = repositoryName).toEntity()

	override suspend fun signIn(token: String): UserInfo =
		api.signIn("Bearer $token").toEntity()
}