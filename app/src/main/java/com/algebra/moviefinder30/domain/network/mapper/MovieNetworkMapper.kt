package com.algebra.moviefinder30.domain.network.mapper

import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear

class MovieNetworkMapper: EntityMapper<SearchNetworkEntity, Movie> {

    override fun mapFromEntity(entity: SearchNetworkEntity): Movie {
        return Movie(checkValue(entity.Title), checkYear(entity.Year), checkValue(entity.Poster), checkValue(entity.imdbID))
    }

    private fun mapToEntity(domainModel: Movie): SearchNetworkEntity {
        return SearchNetworkEntity(domainModel.pictureURL, domainModel.title,"", domainModel.year, domainModel.imdbId)
    }

    fun toEntityListMovie(initial: List<SearchNetworkEntity>) =
        initial.map { mapFromEntity(it) }

    fun toEntityListSearchNetworkEntity(initial: List<Movie>)  =
        initial.map { mapToEntity(it) }

}