package com.algebra.moviefinder30.domain.usecase.db.search

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class GetSearchMovieByYear(private val searchRepo: MovieSearchRepository) : BaseUseCase<String, List<Movie>> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<List<Movie>>) {
        try {
            val result = searchRepo.getSearchMoviesByYear(params)
            callback.onSuccess(result)
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
