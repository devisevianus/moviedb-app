package com.devis.moviedb.feature.main.di

import com.devis.moviedb.data.repository.MainRepository
import com.devis.moviedb.data.source.remote.RemoteMainDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by devisevianus on 23/02/22
 */

@Module
class MainModule {
    @Provides
    fun providesMainRepository(
        remoteMainDataSource: RemoteMainDataSource
    ): MainRepository {
        return MainRepository.MainRepositoryImpl(remoteMainDataSource)
    }
}