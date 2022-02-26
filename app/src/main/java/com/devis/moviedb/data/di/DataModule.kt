package com.devis.moviedb.data.di

import android.content.Context
import com.devis.moviedb.BuildConfig
import com.devis.moviedb.data.source.ApiClient
import com.devis.moviedb.data.source.remote.GenreService
import com.devis.moviedb.data.source.remote.MoviesService
import com.devis.moviedb.data.utils.AuthInterceptor
import dagger.Module
import dagger.Provides

/**
 * Created by devisevianus on 23/02/22
 */

@Module
class DataModule {

    // AuthInterceptor
    @Provides
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor(apiKey = BuildConfig.API_KEY)
    }

    // Service
    @Provides
    fun providesGenreService(
        context: Context,
        authInterceptor: AuthInterceptor
    ): GenreService {
        return ApiClient.retrofitClient(
            context,
            url = BuildConfig.BASE_URL,
            authInterceptor = authInterceptor
        ).create(GenreService::class.java)
    }

    @Provides
    fun providesMoviesService(
        context: Context,
        authInterceptor: AuthInterceptor
    ): MoviesService {
        return ApiClient.retrofitClient(
            context,
            url = BuildConfig.BASE_URL,
            authInterceptor = authInterceptor
        ).create(MoviesService::class.java)
    }

}