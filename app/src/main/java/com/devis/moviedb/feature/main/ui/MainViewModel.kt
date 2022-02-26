package com.devis.moviedb.feature.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devis.moviedb.core.base.BaseViewState
import com.devis.moviedb.core.model.GenreListMdl
import com.devis.moviedb.core.utils.AppDispatchers
import com.devis.moviedb.data.repository.MainRepository
import com.devis.moviedb.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by devisevianus on 23/02/22
 */

class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private var jobCallApi: Job? = null

    private val _mGenreListResult = MutableLiveData<BaseViewState<GenreListMdl>>()

    val genreListResult: LiveData<BaseViewState<GenreListMdl>> = _mGenreListResult

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getGenreList() {
        _mGenreListResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getGenreList()
            }
            when (request) {
                is ResultState.Success -> {
                    _mGenreListResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mGenreListResult.value = BaseViewState.Error(request.errorMessage)
                }
                else -> {}
            }
        }
    }

}