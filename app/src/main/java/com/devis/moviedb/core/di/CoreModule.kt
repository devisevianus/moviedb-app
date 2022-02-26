package com.devis.moviedb.core.di

import android.content.Context
import com.devis.moviedb.core.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

/**
 * Created by devisevianus on 23/02/22
 */

@Module
class CoreModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    fun providesAppDispatcher(): AppDispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO)

}