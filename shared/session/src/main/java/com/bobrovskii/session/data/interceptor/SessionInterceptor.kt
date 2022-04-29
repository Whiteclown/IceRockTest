package com.bobrovskii.session.data.interceptor

import com.bobrovskii.core.Headers
import com.bobrovskii.session.domain.repository.LoginRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class SessionInterceptor @Inject constructor(
	private val repository: LoginRepository,
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		val token = repository.get().authToken

		val authRequest: Request = token?.let {
			request
				.newBuilder()
				.addHeader(Headers.AUTHORIZATION, "Bearer $token")
				.build()
		} ?: request
			.newBuilder()
			.build()

		return chain.proceed(authRequest)
	}
}