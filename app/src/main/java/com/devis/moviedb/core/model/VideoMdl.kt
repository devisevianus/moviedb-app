package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 24/02/22
 */

data class VideoMdl(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null
)

data class VideoListMdl(
    @SerializedName("results")
    val results: MutableList<VideoMdl>? = null
)