package com.bobrovskii.icerocktest.di

import android.content.Context
import com.bobrovskii.core.Authorized
import com.bobrovskii.core.NotAuthorized
import com.bobrovskii.session.data.interceptor.NetworkConnectionInterceptor
import com.bobrovskii.session.data.interceptor.SessionInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
		HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

	@Provides
	@Singleton
	fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor =
		NetworkConnectionInterceptor(context)

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		sessionInterceptor: SessionInterceptor,
		networkConnectionInterceptor: NetworkConnectionInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(sessionInterceptor)
			.addInterceptor(networkConnectionInterceptor)
			.build()

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		networkConnectionInterceptor: NetworkConnectionInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(networkConnectionInterceptor)
			.build()

	private val contentType = "application/json".toMediaType()

	private val json = Json {
		ignoreUnknownKeys = true
	}

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedRetrofit(@NotAuthorized client: OkHttpClient): Retrofit =
		Retrofit
			.Builder()
			.baseUrl("https://api.github.com")
			.addConverterFactory(json.asConverterFactory(contentType))
			.client(client)
			.build()

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedRetrofit(@Authorized client: OkHttpClient): Retrofit =
		Retrofit
			.Builder()
			.baseUrl("https://api.github.com")
			.addConverterFactory(json.asConverterFactory(contentType))
			.client(client)
			.build()
}