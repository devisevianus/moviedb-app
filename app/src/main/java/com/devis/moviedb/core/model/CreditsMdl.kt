package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 24/02/22
 */

data class CreditsMdl(
    @SerializedName("cast")
    val cast: List<CastMdl>? = null
)

data class CastMdl(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("cast_id")
    val castId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    @SerializedName("character")
    val character: String? = null
)