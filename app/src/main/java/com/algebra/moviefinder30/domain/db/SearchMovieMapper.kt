package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear

class SearchMovieMapper: EntityMapper<SearchNetworkEntity, Search> {

    var searchValue = ""

    override fun mapFromEntity(entity: SearchNetworkEntity): Search {
        return Search(0, checkValue(entity.Title), checkYear(entity.Year), checkValue(entity.Poster), checkValue(entity.imdbID), searchValue)
    }

    fun toEntityListSearchMovie(initial: List<SearchNetworkEntity>): List<Search>
            = initial.map { mapFromEntity(it) }
}