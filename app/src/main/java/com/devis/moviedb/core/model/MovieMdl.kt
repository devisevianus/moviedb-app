package com.devis.moviedb.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by devisevianus on 24/02/22
 */

data class MovieMdl(
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: Any? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Float? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("videos")
    val videos: VideoListMdl? = null,
    @SerializedName("credits")
    val credits: CreditsMdl? = null,
    @SerializedName("reviews")
    val reviews: ReviewListMdl? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null
)

data class MovieListMdl(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieMdl>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    var isLast: Boolean = true
)