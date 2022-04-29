package com.bobrovskii.icerocktest.di

import com.bobrovskii.authorization.presentation.AuthRouter
import com.bobrovskii.detailinfo.presentation.DetailsRouter
import com.bobrovskii.icerocktest.navigation.Navigator
import com.bobrovskii.repositorieslist.presentation.RepositoriesListRouter
import com.bobrovskii.splash.presentation.SplashRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

	@Provides
	@Singleton
	fun provideNavigator(): Navigator =
		Navigator()

	@Provides
	@Singleton
	fun provideAuthRouter(navigator: Navigator): AuthRouter =
		navigator

	@Provides
	@Singleton
	fun provideRepositoriesListRouter(navigator: Navigator): RepositoriesListRouter =
		navigator

	@Provides
	@Singleton
	fun provideDetailsRouter(navigator: Navigator): DetailsRouter =
		navigator

	@Provides
	@Singleton
	fun provideSplashRouter(navigator: Navigator): SplashRouter =
		navigator
}