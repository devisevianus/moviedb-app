package com.devis.moviedb.data.source.remote

import com.devis.moviedb.core.model.GenreListMdl
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by devisevianus on 23/02/22
 */

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): Response<GenreListMdl>

}