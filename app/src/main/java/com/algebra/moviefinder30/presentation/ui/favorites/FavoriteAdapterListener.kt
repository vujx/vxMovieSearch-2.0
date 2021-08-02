package com.algebra.moviefinder30.presentation.ui.favorites

interface FavoriteAdapterListener {

    fun onFavClick(imdbId: String)
    fun onItemClick(imdbId: String, title: String)
}
