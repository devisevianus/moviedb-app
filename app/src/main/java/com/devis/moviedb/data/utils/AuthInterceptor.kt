package com.devis.moviedb.data.utils

import com.devis.moviedb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by devisevianus on 23/02/22
 */

class AuthInterceptor @Inject constructor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val authorizedRequestBuilder = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(authorizedRequestBuilder)
    }

}