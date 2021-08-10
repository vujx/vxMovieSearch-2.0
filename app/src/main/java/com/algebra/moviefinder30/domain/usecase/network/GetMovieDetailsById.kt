package com.algebra.moviefinder30.domain.usecase.network

import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase
import java.lang.Exception
import java.lang.NullPointerException

class GetMovieDetailsById(private val movieRepo: MovieNetworkRepository) :
    BaseUseCase<String, MovieDetails> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<MovieDetails>) {
        return try {
            val movieDetails = movieRepo.getMovieDetailsById(params)
            movieDetails?.let {
                callback.onSuccess(it)
            } ?: callback.onError(NullPointerException())
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
