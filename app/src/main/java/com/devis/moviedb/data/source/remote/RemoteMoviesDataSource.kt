package com.devis.moviedb.data.source.remote

import com.devis.moviedb.core.model.MovieListMdl
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewListMdl
import com.devis.moviedb.data.source.MoviesDataSource
import com.devis.moviedb.data.utils.ResultState
import com.devis.moviedb.data.utils.fetchState
import com.devis.moviedb.data.utils.handleError
import javax.inject.Inject

/**
 * Created by devisevianus on 24/02/22
 */

class RemoteMoviesDataSource @Inject constructor(
    private val moviesService: MoviesService
) : MoviesDataSource {

    override suspend fun getMovieList(genreId: Int, page: Int): ResultState<MovieListMdl> {
        return fetchState {
            val response = moviesService.getMovieList(genreId, page)
            val data: MovieListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data.apply {
                    isLast = page == totalPages || totalPages == 0
                })
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): ResultState<MovieMdl> {
        return fetchState {
            val response = moviesService.getMovieDetail(movieId)
            val data: MovieMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): ResultState<ReviewListMdl> {
        return fetchState {
            val response = moviesService.getMovieReviews(movieId, page)
            val data: ReviewListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data.apply {
                    isLast = page == totalPages || totalPages == 0
                })
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }

}