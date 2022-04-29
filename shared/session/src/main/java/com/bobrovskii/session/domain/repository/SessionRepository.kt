package com.bobrovskii.session.domain.repository

import com.bobrovskii.session.domain.entity.Readme
import com.bobrovskii.session.domain.entity.Repo
import com.bobrovskii.session.domain.entity.RepoDetails
import com.bobrovskii.session.domain.entity.UserInfo

interface SessionRepository {

	suspend fun getRepositories(): List<Repo>

	suspend fun getRepository(ownerName: String, repositoryName: String): RepoDetails

	suspend fun getRepositoryReadme(ownerName: String, repositoryName: String): Readme

	suspend fun signIn(token: String): UserInfo
}