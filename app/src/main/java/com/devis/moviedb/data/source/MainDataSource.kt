package com.devis.moviedb.data.source

import com.devis.moviedb.core.model.GenreListMdl
import com.devis.moviedb.data.utils.ResultState

/**
 * Created by devisevianus on 23/02/22
 */

interface MainDataSource {
    suspend fun getGenreList(): ResultState<GenreListMdl>
}