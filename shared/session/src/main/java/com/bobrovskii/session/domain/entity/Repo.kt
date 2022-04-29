package com.bobrovskii.session.domain.entity

data class Repo(
	val name: String,
	val owner: String,
	val description: String?,
	val language: String,
)