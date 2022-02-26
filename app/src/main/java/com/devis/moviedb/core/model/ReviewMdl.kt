package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 24/02/22
 */

data class ReviewMdl(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("author_details")
    val authorDetails: AuthorDetailMdl
)

data class AuthorDetailMdl(
    @SerializedName("avatar_path")
    val avatarPath: String? = null
) {
    val avatarUrl: String
        get() = avatarPath?.drop(1).orEmpty()
}

data class ReviewListMdl(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<ReviewMdl>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    var isLast: Boolean = true
)