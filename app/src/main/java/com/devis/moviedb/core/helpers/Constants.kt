package com.devis.moviedb.core.helpers

/**
 * Created by devisevianus on 24/02/22
 */

object Constants {
    private const val IMAGE_SIZE_W185 = "w185"
    private const val IMAGE_SIZE_W342 = "w342"
    private const val IMAGE_SIZE_W780 = "w780"
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

    const val IMAGE_URL = BASE_IMAGE_URL + IMAGE_SIZE_W185
    const val IMAGE_URL_SIZE_342 = BASE_IMAGE_URL + IMAGE_SIZE_W342
    const val IMAGE_URL_BACKDROP = BASE_IMAGE_URL + IMAGE_SIZE_W780
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_JPG_FORMAT = "/hqdefault.jpg"
    const val YOUTUBE_WEB_URL = "https://www.youtube.com/watch?v="
}