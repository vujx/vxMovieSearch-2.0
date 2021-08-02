package com.algebra.moviefinder30.presentation.ui.search

import com.algebra.moviefinder30.domain.model.remote.Movie

interface SearchAdapterListener {

    fun onFavClick(movie: Movie, isFav: Boolean)
    fun onItemClick(imdb: String, isFav: Boolean)
}
