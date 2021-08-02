package com.algebra.moviefinder30.domain.usecase.network

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception

class GetMovieByYear(private val movieRepo: MovieNetworkRepository) :
    BaseUseCase<String, List<Movie>?> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<List<Movie>?>) {
        return try {
            val movies = movieRepo.getMoviesByYear(params)
            callback.onSuccess(movies)
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
