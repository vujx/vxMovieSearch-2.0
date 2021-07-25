package com.algebra.moviefinder30.data.usecase

import com.algebra.moviefinder30.domain.usecase.db.search.GetSearchMovieByYear
import com.algebra.moviefinder30.domain.usecase.db.search.InsertSearchMovie

data class UseCaseDbSearch(
    val getSearchMovieByYear: GetSearchMovieByYear,
    val insertSearchMovie: InsertSearchMovie
)