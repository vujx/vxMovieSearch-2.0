package com.algebra.moviefinder30.domain.usecase.db.search

import com.algebra.moviefinder30.data.model.local.SearchEntity
import com.algebra.moviefinder30.data.model.remote.movie.SearchNetworkEntity
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class InsertSearchMovie(private val searchRepo: MovieSearchRepository): BaseUseCase<Movie, String> {

    override suspend fun execute(params: Movie, callback: BaseUseCase.Callback<String>) {
        return try{
            searchRepo.insertSearchMovie(params)
            callback.onSuccess("Search movie successfully added!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}