package com.bobrovskii.session.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
	val login: String,
)