package com.bobrovskii.session.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
	val full_name: String,
	val description: String?,
	val language: String,
)