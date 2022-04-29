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
		name = full_name.split("/")[1],
		owner = full_name.split("/")[0],
		htmlUrl = html_url,
		license = license,
		stargazersCount = stargazers_count,
		forks = forks,
		watchersCount = watchers_count,
	)

fun RepoDto.toEntity() =
	Repo(
		name = full_name.split("/")[1],
		owner = full_name.split("/")[0],
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