package com.devis.moviedb.core.base

/**
 * Created by devisevianus on 23/02/22
 */

sealed class BaseViewState<out R> {
    object Loading : BaseViewState<Nothing>()
    data class Error(val errorMessage: String?) : BaseViewState<Nothing>()
    data class Success<T>(
        val data: T?,
        val isLast: Boolean? = false
    ) : BaseViewState<T>()
}