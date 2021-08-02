package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.data.db.FavoriteMovieDao
import com.algebra.moviefinder30.data.db.SearchDao
import com.algebra.moviefinder30.data.network.MovieService
import com.algebra.moviefinder30.data.repository.DefaultFavoriteRepository
import com.algebra.moviefinder30.data.repository.DefaultMovieNetworkRepository
import com.algebra.moviefinder30.data.repository.DefaultSearchRepository
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieRepository
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryNetworkModule {

    @Provides
    @Singleton
    fun provideNetworkRepo(apiService: MovieService) = MovieNetworkRepository(DefaultMovieNetworkRepository(apiService))

    @Provides
    @Singleton
    fun provideDbFavoriteRepo(favoriteDao: FavoriteMovieDao) = FavoriteMovieRepository(DefaultFavoriteRepository(favoriteDao))

    @Provides
    @Singleton
    fun provideDBSearchRepo(searchDao: SearchDao) = MovieSearchRepository(DefaultSearchRepository(searchDao))
}
