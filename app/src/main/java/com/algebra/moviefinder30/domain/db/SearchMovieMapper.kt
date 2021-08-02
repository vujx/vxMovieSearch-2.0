package com.algebra.moviefinder30.domain.db

import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.domain.EntityMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.util.checkPictureURL
import com.algebra.moviefinder30.domain.util.checkValue
import com.algebra.moviefinder30.domain.util.checkYear
import com.algebra.moviefinder30.presentation.ui.MainActivity
import java.util.*

class SearchMovieMapper : EntityMapper<Movie, SearchEntity> {

    override fun mapFromEntity(entity: Movie): SearchEntity =
        SearchEntity(0, checkValue(entity.title), checkYear(entity.year), checkPictureURL(entity.pictureURL), checkValue(entity.imdbId), MainActivity.searchValue.toLowerCase(Locale.ROOT))

    private fun mapFromSearchEntityToMovie(entity: SearchEntity) = Movie(entity.title, entity.year, entity.pictureURL, entity.imdbId)

    fun toEntityListMovie(initial: List<SearchEntity>): List<Movie> = initial.map { mapFromSearchEntityToMovie(it) }
}
