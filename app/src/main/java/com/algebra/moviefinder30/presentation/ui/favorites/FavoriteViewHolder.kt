package com.algebra.moviefinder30.presentation.ui.favorites

import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.FavoriteMovieItemBinding
import com.algebra.moviefinder30.util.displayPic

class FavoriteViewHolder(
    private val itemFavorite: FavoriteMovieItemBinding,
    private val listener: FavoriteAdapterListener,
    private val listOfFavoriteMovies: List<FavoriteMovieEntity>
) : RecyclerView.ViewHolder(itemFavorite.root) {
    init {
        itemFavorite.ivFavorite.setOnClickListener {
            listener.onFavClick(listOfFavoriteMovies[layoutPosition].imdbId)
        }

        itemView.setOnClickListener {
            listener.onItemClick(
                listOfFavoriteMovies[layoutPosition].imdbId,
                listOfFavoriteMovies[layoutPosition].title
            )
        }
    }

    fun bind(favoriteMovie: FavoriteMovieEntity) {
        itemFavorite.tvTitle.text = favoriteMovie.title
        itemFavorite.tvYear.text = favoriteMovie.year
        displayPic(itemView, favoriteMovie.pictureURL, itemFavorite.imMovie)
    }
}
