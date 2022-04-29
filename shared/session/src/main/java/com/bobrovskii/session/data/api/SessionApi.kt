package com.bobrovskii.session.data.api

import com.bobrovskii.core.Headers
import com.bobrovskii.session.data.dto.ReadmeDto
import com.bobrovskii.session.data.dto.RepoDetailsDto
import com.bobrovskii.session.data.dto.RepoDto
import com.bobrovskii.session.data.dto.UserInfoDto
import com.bobrovskii.session.domain.entity.Readme
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SessionApi {

	@GET("/user/repos")
	suspend fun getRepositories(): List<RepoDto>

	@GET("/repos/{owner}/{repo}")
	suspend fun getRepository(@Path("owner") ownerName: String, @Path("repo") repositoryName: String): RepoDetailsDto

	@GET("/repos/{owner}/{repo}/readme")
	suspend fun getRepositoryReadme(@Path("owner") ownerName: String, @Path("repo") repositoryName: String): ReadmeDto

	@GET("/user")
	suspend fun signIn(@Header(Headers.AUTHORIZATION) token: String): UserInfoDto
}