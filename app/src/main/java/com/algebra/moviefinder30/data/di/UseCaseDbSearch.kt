package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.data.usecase.UseCaseDbSearch
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.usecase.db.search.GetSearchMovieByYear
import com.algebra.moviefinder30.domain.usecase.db.search.InsertSearchMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseDbSearch {

    @Provides
    @ViewModelScoped
    fun provideUseCaseDbSearch(repo: MovieSearchRepository) =
        UseCaseDbSearch(
            GetSearchMovieByYear(repo),
            InsertSearchMovie(repo)
        )
}
