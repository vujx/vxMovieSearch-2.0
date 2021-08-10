package com.algebra.moviefinder30.data

import androidx.lifecycle.MutableLiveData
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.favorite.FavoriteMovieLocalDataSource

class FakeFavoriteRepository : FavoriteMovieLocalDataSource {

    private val favoriteMovies = mutableListOf<FavoriteMovieEntity>()

    private val observableFavoriteMovies: MutableLiveData<List<FavoriteMovieEntity>> = MutableLiveData(favoriteMovies)

    private fun refreshLiveData() {
        observableFavoriteMovies.postValue(favoriteMovies)
    }
    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovies.add(FavoriteMovieMapper().mapFromEntity(movie))
        refreshLiveData()
    }

    override suspend fun getAllFavoritesMovies(): List<FavoriteMovieEntity> = favoriteMovies

    override suspend fun removeFavoriteMovie(imdb: String) {
        for (i: Int in 0 until favoriteMovies.size) {
            if (favoriteMovies[i].imdbId == imdb) {
                favoriteMovies.removeAt(i)
                break
            }
        }
        refreshLiveData()
    }

    override suspend fun removeAllFavoritesMovie() {
        favoriteMovies.clear()
        refreshLiveData()
    }
}
