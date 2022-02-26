package com.devis.moviedb.data.repository

import com.devis.moviedb.core.model.MovieListMdl
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewListMdl
import com.devis.moviedb.data.source.MoviesDataSource
import com.devis.moviedb.data.utils.ResultState
import javax.inject.Inject

/**
 * Created by devisevianus on 24/02/22
 */

interface MoviesRepository {
    suspend fun getMovieList(genreId: Int, page: Int): ResultState<MovieListMdl>
    suspend fun getMovieDetail(movieId: Int): ResultState<MovieMdl>
    suspend fun getMovieReviews(movieId: Int, page: Int): ResultState<ReviewListMdl>

    class MovieRepositoryImpl @Inject constructor(
        private val remoteMoviesDataSource: MoviesDataSource
    ) : MoviesRepository {

        override suspend fun getMovieList(genreId: Int, page: Int): ResultState<MovieListMdl> {
            return remoteMoviesDataSource.getMovieList(genreId, page)
        }

        override suspend fun getMovieDetail(movieId: Int): ResultState<MovieMdl> {
            return remoteMoviesDataSource.getMovieDetail(movieId)
        }

        override suspend fun getMovieReviews(movieId: Int, page: Int): ResultState<ReviewListMdl> {
            return remoteMoviesDataSource.getMovieReviews(movieId, page)
        }

    }

}