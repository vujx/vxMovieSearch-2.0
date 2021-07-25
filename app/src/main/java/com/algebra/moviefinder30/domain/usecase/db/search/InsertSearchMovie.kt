package com.algebra.moviefinder30.domain.usecase.db.search

import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class InsertSearchMovie(private val searchRepo: MovieSearchRepository): BaseUseCase<Search, String> {

    override suspend fun execute(params: Search, callback: BaseUseCase.Callback<String>) {
        return try{
            searchRepo.insertSearchMovie(params)
            callback.onSuccess("Search movie successfully added!")
        } catch (e: Exception){
            callback.onError(e)
        }
    }

}