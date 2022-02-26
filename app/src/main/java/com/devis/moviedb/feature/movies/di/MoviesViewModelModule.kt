package com.devis.moviedb.feature.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devis.moviedb.core.base.BaseViewModelFactory
import com.devis.moviedb.core.base.ViewModelKey
import com.devis.moviedb.feature.movies.ui.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by devisevianus on 24/02/22
 */

@Module
abstract class MoviesViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    internal abstract fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel
}