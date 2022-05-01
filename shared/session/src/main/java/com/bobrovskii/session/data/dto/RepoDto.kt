package com.bobrovskii.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
	@SerialName("full_name") val fullName: String,
	val description: String?,
	val language: String,
)