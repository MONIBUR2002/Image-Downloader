package com.moniapps.imagedownloader.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor
@Inject constructor(
    @Named("API_KEY")
    private val api_key: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", api_key)
            .method(original.method, original.body)
            .build()
        return chain.proceed(requestBuilder)
    }
}