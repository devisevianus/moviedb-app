package com.devis.moviedb.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by devisevianus on 23/02/22
 */

class AppDispatchers @Inject constructor(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)