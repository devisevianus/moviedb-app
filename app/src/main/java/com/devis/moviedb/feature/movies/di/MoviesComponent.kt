package com.devis.moviedb.feature.movies.di

import com.devis.moviedb.core.di.CoreModule
import com.devis.moviedb.data.di.DataModule
import com.devis.moviedb.feature.movies.ui.detail.MovieDetailActivity
import com.devis.moviedb.feature.movies.ui.list.MovieListActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by devisevianus on 24/02/22
 */

@Singleton
@Component(
    modules = [
        CoreModule::class,
        DataModule::class,
        MoviesModule::class,
        MoviesViewModelModule::class
    ]
)
interface MoviesComponent {

    fun inject(activity: MovieListActivity)

    fun inject(activity: MovieDetailActivity)

}