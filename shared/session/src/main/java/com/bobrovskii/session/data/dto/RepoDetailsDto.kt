package com.bobrovskii.session.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepoDetailsDto(
	val full_name: String,
	val html_url: String,
	val license: String?,
	val stargazers_count: Int,
	val forks: Int,
	val watchers_count: Int,
)