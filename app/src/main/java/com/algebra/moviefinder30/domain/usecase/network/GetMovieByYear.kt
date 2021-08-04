package com.algebra.moviefinder30.domain.usecase.network

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class GetMovieByYear(private val movieRepo: MovieNetworkRepository, private val searchRepo: MovieSearchRepository) :
    BaseUseCase<String, List<Movie>> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<List<Movie>>) {
        return try {
            val searchMovies = searchRepo.getSearchMoviesByYear(params)
            if (searchMovies.isEmpty()) {
                val movies = movieRepo.getMoviesByYear(params)
                movies?.forEach {
                    searchRepo.insertSearchMovie(it)
                }
                if (movies != null) callback.onSuccess(movies)
                else callback.onError(Throwable() as NullPointerException)
            } else callback.onSuccess(searchMovies)
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
