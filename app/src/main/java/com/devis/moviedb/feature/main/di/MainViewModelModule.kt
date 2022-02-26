package com.devis.moviedb.feature.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devis.moviedb.core.base.BaseViewModelFactory
import com.devis.moviedb.core.base.ViewModelKey
import com.devis.moviedb.feature.main.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by devisevianus on 23/02/22
 */

@Module
abstract class MainViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}