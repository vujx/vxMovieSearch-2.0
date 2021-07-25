package com.algebra.moviefinder30.domain.usecase.db.search

import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchLocalDataSource
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchRepository
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class GetSearchMovieByYear(private val searchRepo: MovieSearchRepository): BaseUseCase<String, List<Search>> {

    override suspend fun execute(params: String, callback: BaseUseCase.Callback<List<Search>>) {
        try{
            val result = searchRepo.getSearchMoviesByYear(params)
            callback.onSuccess(result)
        } catch(e: Exception){
            callback.onError(e)
        }
    }

}