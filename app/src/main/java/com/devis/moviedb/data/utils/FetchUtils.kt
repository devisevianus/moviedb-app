package com.devis.moviedb.data.utils

import java.net.ConnectException

/**
 * Created by devisevianus on 23/02/22
 */

suspend fun <T : Any> fetchState(call: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        call.invoke()
    } catch (e: ConnectException) {
        ResultState.Error(e.handleError().message)
    } catch (e: Exception) {
        ResultState.Error(e.handleError().message)
    } catch (e: Throwable) {
        ResultState.Error(e.handleError().message)

    }
}