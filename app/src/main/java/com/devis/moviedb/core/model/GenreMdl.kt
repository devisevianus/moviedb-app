package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 23/02/22
 */

data class GenreListMdl(
    @SerializedName("genres")
    val genres: List<MasterDataMdl>? = null
)