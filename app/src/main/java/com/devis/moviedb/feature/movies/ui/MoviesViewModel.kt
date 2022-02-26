package com.devis.moviedb.feature.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devis.moviedb.core.base.BaseViewState
import com.devis.moviedb.core.model.MovieListMdl
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewListMdl
import com.devis.moviedb.core.utils.AppDispatchers
import com.devis.moviedb.data.repository.MoviesRepository
import com.devis.moviedb.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by devisevianus on 24/02/22
 */

class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private var jobCallApiMovie: Job? = null
    private var jobCallApiReview: Job? = null

    private val _mMovieListResult = MutableLiveData<BaseViewState<MovieListMdl>>()
    private val _mMovieDetailResult = MutableLiveData<BaseViewState<MovieMdl>>()
    private val _mMovieReviewsResult = MutableLiveData<BaseViewState<ReviewListMdl>>()

    val movieListResult: LiveData<BaseViewState<MovieListMdl>> = _mMovieListResult
    val movieDetailResult: LiveData<BaseViewState<MovieMdl>> = _mMovieDetailResult
    val movieReviewsResult: LiveData<BaseViewState<ReviewListMdl>> = _mMovieReviewsResult

    override fun onCleared() {
        super.onCleared()
        jobCallApiMovie?.cancel()
        jobCallApiReview?.cancel()
    }

    fun getMovieList(genreId : Int, page : Int = 1) {
        _mMovieListResult.value = BaseViewState.Loading
        jobCallApiMovie?.cancel()
        jobCallApiMovie = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieList(genreId, page)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieListResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieListResult.value = BaseViewState.Error(request.errorMessage)
                }
                else -> {}
            }
        }
    }

    fun getMovieDetail(movieId : Int) {
        _mMovieDetailResult.value = BaseViewState.Loading
        jobCallApiMovie?.cancel()
        jobCallApiMovie = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieDetail(movieId)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieDetailResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieDetailResult.value = BaseViewState.Error(request.errorMessage)
                }
                else -> {}
            }
        }
    }

    fun getMovieReviews(movieId : Int, page: Int = 1) {
        _mMovieReviewsResult.value = BaseViewState.Loading
        jobCallApiReview?.cancel()
        jobCallApiReview = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieReviews(movieId, page)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieReviewsResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieReviewsResult.value = BaseViewState.Error(request.errorMessage)
                }
                else -> {}
            }
        }
    }

}