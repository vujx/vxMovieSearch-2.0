package com.algebra.moviefinder30.domain.usecase.db.search

import com.algebra.moviefinder30.domain.model.local.Search
import com.algebra.moviefinder30.domain.repository.db.search.MovieSearchLocalDataSource
import com.algebra.moviefinder30.domain.usecase.BaseUseCase

class GetSearchMovieByYear(private val dataSource: MovieSearchLocalDataSource): BaseUseCase<Int?, List<Search>> {

    override suspend fun execute(params: Int?, callback: BaseUseCase.Callback<List<Search>>) {
        try{
            val result = dataSource.getSearchMoviesByYear()
            callback.onSuccess(result)
        } catch(e: Exception){
            callback.onError(e)
        }
    }

}