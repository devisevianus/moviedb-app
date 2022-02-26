package com.devis.moviedb.data.source.remote

import com.devis.moviedb.core.model.GenreListMdl
import com.devis.moviedb.data.source.MainDataSource
import com.devis.moviedb.data.utils.ResultState
import com.devis.moviedb.data.utils.fetchState
import com.devis.moviedb.data.utils.handleError
import javax.inject.Inject

/**
 * Created by devisevianus on 23/02/22
 */

class RemoteMainDataSource @Inject constructor(
    private val genreService: GenreService
) : MainDataSource {

    override suspend fun getGenreList(): ResultState<GenreListMdl> {
        return fetchState {
            val response = genreService.getGenreList()
            val data: GenreListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }

}