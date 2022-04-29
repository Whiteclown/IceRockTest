package com.bobrovskii.session.domain.entity

data class RepoDetails(
	val name: String,
	val owner: String,
	val htmlUrl: String,
	val license: String?,
	val stargazersCount: Int,
	val forks: Int,
	val watchersCount: Int,
)