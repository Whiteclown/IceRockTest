package com.bobrovskii.session.data.mapper

import com.bobrovskii.session.data.dto.ReadmeDto
import com.bobrovskii.session.data.dto.RepoDetailsDto
import com.bobrovskii.session.data.dto.RepoDto
import com.bobrovskii.session.data.dto.UserInfoDto
import com.bobrovskii.session.domain.entity.Readme
import com.bobrovskii.session.domain.entity.Repo
import com.bobrovskii.session.domain.entity.RepoDetails
import com.bobrovskii.session.domain.entity.UserInfo

fun RepoDetailsDto.toEntity() =
	RepoDetails(
		name = fullName.split("/")[1],
		owner = fullName.split("/")[0],
		htmlUrl = htmlUrl,
		license = license?.name,
		stargazersCount = stargazersCount,
		forks = forks,
		watchersCount = watchersCount,
	)

fun RepoDto.toEntity() =
	Repo(
		name = fullName.split("/")[1],
		owner = fullName.split("/")[0],
		description = description,
		language = language,
	)

fun UserInfoDto.toEntity() =
	UserInfo(
		username = login,
	)

fun ReadmeDto.toEntity() =
	Readme(
		content = content,
	)