package com.algebra.moviefinder30.domain.network.mapper

import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.util.checkPictureURL
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear

class MovieNetworkMapper : EntityMapper<SearchNetworkEntity, Movie> {

    override fun mapFromEntity(entity: SearchNetworkEntity): Movie {
        return Movie(checkValue(entity.Title), checkYear(entity.Year), checkPictureURL(entity.Poster), checkValue(entity.imdbID))
    }

    fun toEntityListMovie(initial: List<SearchNetworkEntity>) =
        initial.map { mapFromEntity(it) }

    fun toEntityListMovieByYear(initial: List<SearchNetworkEntity>) =
        initial.map { mapFromEntity(it) }.sortedBy { it.year }
}
