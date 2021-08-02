package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.usecase.db.favorite.GetAllFavoriteMovies
import com.algebra.moviefinder30.domain.usecase.db.favorite.InsertFavoriteMovie
import com.algebra.moviefinder30.domain.usecase.db.favorite.RemoveAllFavoriteMovie
import com.algebra.moviefinder30.domain.usecase.db.favorite.RemoveFavoriteMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseDbFavorite {

    @Provides
    @ViewModelScoped
    fun provideUseCaseDbFavorite(repo: FavoriteMovieRepository) =
        UseCaseDbFavorite(
            GetAllFavoriteMovies(repo),
            InsertFavoriteMovie(repo),
            RemoveAllFavoriteMovie(repo),
            RemoveFavoriteMovie(repo)
        )
}
