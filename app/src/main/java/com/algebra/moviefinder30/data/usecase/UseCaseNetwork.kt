package com.algebra.moviefinder30.data.usecase

import com.algebra.moviefinder30.domain.usecase.network.GetMovieByYear
import com.algebra.moviefinder30.domain.usecase.network.GetMovieDetailsById

data class UseCaseNetwork(
    val getMovieByYear: GetMovieByYear,
    val getMovieDetailsById: GetMovieDetailsById
)
