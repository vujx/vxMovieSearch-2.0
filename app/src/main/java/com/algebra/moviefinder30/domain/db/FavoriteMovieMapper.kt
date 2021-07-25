package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.domain.model.local.FavoriteMovie
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.EntityMapper

class FavoriteMovieMapper: EntityMapper<Movie, FavoriteMovie> {

    override fun mapFromEntity(entity: Movie): FavoriteMovie {
        return FavoriteMovie(0, entity.title, entity.year, entity.pictureURL, entity.imdbId)
    }

    private fun mapFromDetailsToEntity(entity: MovieDetails): FavoriteMovie{
        return FavoriteMovie(0, entity.title, entity.year, entity.pictureURL, entity.imdbId)
    }

    fun toEntityListFavoriteMovie(initial: List<Movie>): List<FavoriteMovie>
            = initial.map { mapFromEntity(it) }

    fun toEntityListFavoriteMovieFromDetails(initial: List<MovieDetails>): List<FavoriteMovie>
            = initial.map { mapFromDetailsToEntity(it) }
}