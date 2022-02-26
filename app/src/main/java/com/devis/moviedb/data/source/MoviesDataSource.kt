package com.devis.moviedb.data.source

import com.devis.moviedb.core.model.MovieListMdl
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewListMdl
import com.devis.moviedb.data.utils.ResultState
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by devisevianus on 24/02/22
 */

interface MoviesDataSource {

    suspend fun getMovieList(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): ResultState<MovieListMdl>

    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): ResultState<MovieMdl>

    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): ResultState<ReviewListMdl>

}