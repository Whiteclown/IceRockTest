package com.bobrovskii.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetailsDto(
	@SerialName("full_name") val fullName: String,
	@SerialName("html_url") val htmlUrl: String,
	val license: LicenseDto?,
	@SerialName("stargazers_count") val stargazersCount: Int,
	val forks: Int,
	@SerialName("watchers_count") val watchersCount: Int,
) {

	@Serializable
	data class LicenseDto(
		@SerialName("spdx_id") val name: String,
	)
}