package com.devis.moviedb.data.source

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.devis.moviedb.data.utils.AuthInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

/**
 * Created by devisevianus on 23/02/22
 */

object ApiClient {
    fun retrofitClient(
        context: Context,
        url: String,
        authInterceptor: AuthInterceptor? = null): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()

        if (authInterceptor != null) {
            okHttpBuilder.addInterceptor(authInterceptor)
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val hostnameVerifier = HostnameVerifier { hostname, session ->
            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
            hv.verify(hostname, session)
        }

        okHttpBuilder.hostnameVerifier(hostnameVerifier)

        okHttpBuilder.addInterceptor(createLoggingInterceptor())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)

        okHttpBuilder.addInterceptor(ChuckerInterceptor(context))

        val okHttpClient = okHttpBuilder.build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}