package com.devis.moviedb.data.repository

import com.devis.moviedb.core.model.GenreListMdl
import com.devis.moviedb.data.source.MainDataSource
import com.devis.moviedb.data.utils.ResultState
import javax.inject.Inject

/**
 * Created by devisevianus on 23/02/22
 */

interface MainRepository {
    suspend fun getGenreList(): ResultState<GenreListMdl>

    class MainRepositoryImpl @Inject constructor(
        private val remoteMainDataSource: MainDataSource
    ) : MainRepository {
        override suspend fun getGenreList(): ResultState<GenreListMdl> {
            return remoteMainDataSource.getGenreList()
        }
    }
}