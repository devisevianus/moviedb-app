package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 23/02/22
 */

data class MasterDataMdl(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)