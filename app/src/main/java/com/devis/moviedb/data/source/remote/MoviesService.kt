package com.devis.moviedb.data.source.remote

import com.devis.moviedb.core.model.MovieListMdl
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewListMdl
import com.devis.moviedb.core.model.ReviewMdl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by devisevianus on 24/02/22
 */

interface MoviesService {

    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<MovieListMdl>

    @GET("movie/{movie_id}?append_to_response=videos,credits")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): Response<MovieMdl>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<ReviewListMdl>

}