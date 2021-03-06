package com.algebra.moviefinder30.domain.usecase

interface BaseUseCase<in P, out R> {
    interface Callback<in R> {
        fun onSuccess(result: R)
        fun onError(throwable: Throwable)
    }

    suspend fun execute(params: P, callback: Callback<R>)
}
