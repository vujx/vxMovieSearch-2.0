package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails

class FavoriteMovieMapper: EntityMapper<Movie, FavoriteMovieEntity> {

    override fun mapFromEntity(entity: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(entity.imdbId, entity.title, entity.year, entity.pictureURL, entity.imdbId)
    }

    private fun mapFromDetailsToEntity(entity: MovieDetails): FavoriteMovieEntity{
        return FavoriteMovieEntity(entity.imdbId, entity.title, entity.year, entity.pictureURL, entity.imdbId)
    }

    fun toEntityListFavoriteMovie(initial: List<Movie>): List<FavoriteMovieEntity>
            = initial.map { mapFromEntity(it) }

    fun toEntityListFavoriteMovieFromDetails(initial: List<MovieDetails>): List<FavoriteMovieEntity>
            = initial.map { mapFromDetailsToEntity(it) }
}