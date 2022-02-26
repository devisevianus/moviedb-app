package com.devis.moviedb.core.model

/**
 * Created by devisevianus on 23/02/22
 */

class ErrorMdl(
    val status: Boolean = false,
    val http_code: Int? = null,
    val message: String? = null
)