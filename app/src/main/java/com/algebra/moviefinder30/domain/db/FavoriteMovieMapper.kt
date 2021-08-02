package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.model.remote.Movie

class FavoriteMovieMapper : EntityMapper<Movie, FavoriteMovieEntity> {

    override fun mapFromEntity(entity: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(entity.imdbId, entity.title, entity.year, entity.pictureURL, entity.imdbId)
    }
}
