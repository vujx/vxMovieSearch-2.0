package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.data.usecase.UseCaseDbSearch
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import com.algebra.moviefinder30.domain.usecase.db.favorite.*
import com.algebra.moviefinder30.domain.usecase.db.search.GetSearchMovieByYear
import com.algebra.moviefinder30.domain.usecase.db.search.InsertSearchMovie
import com.algebra.moviefinder30.domain.usecase.network.GetMovieByTitle
import com.algebra.moviefinder30.domain.usecase.network.GetMovieByYear
import com.algebra.moviefinder30.domain.usecase.network.GetMovieDetailsById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

     @Provides
     @Singleton
     fun provideNetworkUseCase(repo: MovieNetworkRepository) =
         UseCaseNetwork(
             GetMovieByTitle(repo),
             GetMovieByYear(repo),
             GetMovieDetailsById(repo)
         )

    @Provides
    @Singleton
    fun provideUseCaseDbFavorite(repo: FavoriteMovieRepository) =
        UseCaseDbFavorite(
            GetAllFavoriteMovies(repo),
            GetFavoriteMovie(repo),
            InsertFavoriteMovie(repo),
            RemoveAllFavoriteMovie(repo),
            RemoveFavoriteMovie(repo)
        )

    @Provides
    @Singleton
    fun provideUseCaseDbSearch(repo: MovieSearchRepository) =
        UseCaseDbSearch(
            GetSearchMovieByYear(repo),
            InsertSearchMovie(repo)
        )
}