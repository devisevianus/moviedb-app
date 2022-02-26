package com.devis.moviedb.feature.main.di

import com.devis.moviedb.core.di.CoreModule
import com.devis.moviedb.data.di.DataModule
import com.devis.moviedb.feature.main.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by devisevianus on 23/02/22
 */

@Singleton
@Component(
    modules = [
        CoreModule::class,
        DataModule::class,
        MainModule::class,
        MainViewModelModule::class
    ]
)
interface MainComponent {

    fun inject(activity: MainActivity)

}