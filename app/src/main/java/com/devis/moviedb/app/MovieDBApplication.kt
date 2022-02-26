package com.devis.moviedb.app

import android.app.Application
import com.devis.moviedb.BuildConfig
import timber.log.Timber

/**
 * Created by devisevianus on 23/02/22
 */

class MovieDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}