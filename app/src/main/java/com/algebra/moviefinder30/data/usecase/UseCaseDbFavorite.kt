package com.algebra.moviefinder30.data.usecase
import com.algebra.moviefinder30.domain.usecase.db.favorite.*

data class UseCaseDbFavorite(
    val getAllFavoriteMovies: GetAllFavoriteMovies,
    val insertFavoriteMovie: InsertFavoriteMovie,
    val removeAllFavoriteMovie: RemoveAllFavoriteMovie,
    val removeFavoriteMovie: RemoveFavoriteMovie
)
