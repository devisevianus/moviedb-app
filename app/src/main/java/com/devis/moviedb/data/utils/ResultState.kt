package com.devis.moviedb.data.utils

/**
 * Created by devisevianus on 23/02/22
 */

sealed class ResultState<out T : Any?> {
    data class Success<out T : Any?>(
        val data: T?,
        val isLast: Boolean = false) : ResultState<T>()
    data class Error(val errorMessage: String?) : ResultState<Nothing>()
    data class ErrorWithCode(
        val code: Int, val errorMessage: String?) : ResultState<Nothing>()
}