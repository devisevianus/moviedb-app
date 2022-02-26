package com.devis.moviedb.feature.movies.di

import com.devis.moviedb.data.repository.MoviesRepository
import com.devis.moviedb.data.source.remote.RemoteMoviesDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by devisevianus on 24/02/22
 */

@Module
class MoviesModule {
    @Provides
    fun providesMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource
    ): MoviesRepository {
        return MoviesRepository.MovieRepositoryImpl(remoteMoviesDataSource)
    }
}