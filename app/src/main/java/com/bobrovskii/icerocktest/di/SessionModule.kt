package com.bobrovskii.icerocktest.di

import android.content.Context
import com.bobrovskii.core.Authorized
import com.bobrovskii.core.NotAuthorized
import com.bobrovskii.session.data.api.SessionApi
import com.bobrovskii.session.data.repository.LoginRepositoryImpl
import com.bobrovskii.session.data.repository.SessionRepositoryImpl
import com.bobrovskii.session.domain.repository.LoginRepository
import com.bobrovskii.session.domain.repository.SessionRepository
import com.bobrovskii.session.domain.usecase.ClearSessionUseCase
import com.bobrovskii.session.domain.usecase.GetRepositoriesUseCase
import com.bobrovskii.session.domain.usecase.GetSessionUseCase
import com.bobrovskii.session.domain.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionModule {

	@Provides
	@Singleton
	fun providesSessionApi(@Authorized retrofit: Retrofit): SessionApi =
		retrofit.create()

	@Provides
	@Singleton
	fun providesSessionRepository(api: SessionApi): SessionRepository =
		SessionRepositoryImpl(api)

	@Provides
	@Singleton
	fun providesLoginRepository(@ApplicationContext context: Context): LoginRepository =
		LoginRepositoryImpl(context)

	@Provides
	@Singleton
	fun providesSignInUseCase(sessionRepository: SessionRepository, loginRepository: LoginRepository): SignInUseCase =
		SignInUseCase(sessionRepository, loginRepository)

	@Provides
	@Singleton
	fun providesGetSessionUseCase(repository: LoginRepository): GetSessionUseCase =
		GetSessionUseCase(repository)

	@Provides
	@Singleton
	fun providesGetRepositoriesUseCase(repository: SessionRepository): GetRepositoriesUseCase =
		GetRepositoriesUseCase(repository)

	@Provides
	@Singleton
	fun provideClearSessionUseCase(repository: LoginRepository): ClearSessionUseCase =
		ClearSessionUseCase(repository)
}