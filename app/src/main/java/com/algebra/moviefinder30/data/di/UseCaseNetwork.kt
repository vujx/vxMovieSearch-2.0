package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import com.algebra.moviefinder30.domain.usecase.network.GetMovieByYear
import com.algebra.moviefinder30.domain.usecase.network.GetMovieDetailsById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseNetwork {

    @Provides
    @ViewModelScoped
    fun provideNetworkUseCase(repo: MovieNetworkRepository) =
        UseCaseNetwork(
            GetMovieByYear(repo),
            GetMovieDetailsById(repo)
        )
}
