package com.algebra.moviesearching.viewModel

import androidx.lifecycle.ViewModel
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.repository.FavoriteMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val repo: FavoriteMoviesRepository): ViewModel(){

    fun getAllFavoriteMovies()  = repo.getAllFavoriteMovies()

    fun insertMovieToFavorite(movie: FavoriteMovie){
        repo.insertFavoriteMovie(movie)
    }

    fun removeMovieFromFavorite(id: String){
        repo.removeMovie(id)
    }

    suspend fun getAllFavMoviesCour() = repo.getAllFavMovies()
}