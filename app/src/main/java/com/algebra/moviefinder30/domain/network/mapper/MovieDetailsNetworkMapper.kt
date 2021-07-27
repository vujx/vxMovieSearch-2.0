package com.algebra.moviefinder30.domain.network.mapper

import com.algebra.moviefinder30.data.model.remote.details.MovieDetailsNetworkEntity
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.util.checkPictureURL
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear

class MovieDetailsNetworkMapper: EntityMapper<MovieDetailsNetworkEntity, MovieDetails> {

    override fun mapFromEntity(entity: MovieDetailsNetworkEntity): MovieDetails {
        return MovieDetails(checkValue(entity.Title), checkYear(entity.Year), checkPictureURL(entity.Poster),
        checkValue(entity.imdbID), checkValue(entity.Released), checkValue(entity.Runtime), checkValue(entity.imdbRating),
        checkValue(entity.Plot), checkValue(entity.Genre))
    }

    fun toEntityListMovieDetails(initial: List<MovieDetailsNetworkEntity>): List<MovieDetails>
        = initial.map { mapFromEntity(it) }

}