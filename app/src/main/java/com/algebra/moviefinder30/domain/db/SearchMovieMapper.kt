package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.util.checkPictureURL
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear

class SearchMovieMapper: EntityMapper<SearchNetworkEntity, SearchEntity> {

    var searchValue = ""

    override fun mapFromEntity(entity: SearchNetworkEntity): SearchEntity {
        return SearchEntity(0, checkValue(entity.Title), checkYear(entity.Year), checkPictureURL(entity.Poster), checkValue(entity.imdbID), searchValue)
    }

    fun toEntityListSearchMovie(initial: List<SearchNetworkEntity>): List<SearchEntity>
            = initial.map { mapFromEntity(it) }

}