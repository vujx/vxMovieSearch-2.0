package com.algebra.moviefinder30.presentation.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.SearchMovieItemBinding
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.presentation.util.checkIsFav
import com.algebra.moviefinder30.util.displayPic

class SearchMovieViewHolder(
    private val itemMovie: SearchMovieItemBinding,
    private val listener: SearchAdapterListener,
    private val listOfMovies: List<Movie>,
    private val listOfFavMovies: List<FavoriteMovieEntity>
) : RecyclerView.ViewHolder(itemMovie.root) {

    init {
        itemView.setOnClickListener {
            listener.onItemClick(listOfMovies[layoutPosition].imdbId, checkIsFav(layoutPosition, listOfMovies, listOfFavMovies))
        }
        itemMovie.ivFavorite.setOnClickListener {
            listener.onFavClick(listOfMovies[layoutPosition], checkIsFav(layoutPosition, listOfMovies, listOfFavMovies))
        }
    }

    fun bind(movie: Movie) {
        if (adapterPosition == 0 || movie.year != listOfMovies[adapterPosition - 1].year) {
            itemMovie.tvYear.visibility = View.VISIBLE
            itemMovie.tvYear.text = movie.year
        } else itemMovie.tvYear.visibility = View.GONE

        itemMovie.tvTitle.text = movie.title
        displayPic(itemView, movie.pictureURL, itemMovie.imMovie)

        if (FavoriteMovieMapper().mapFromEntity(movie) in listOfFavMovies) itemMovie.ivFavorite.setBackgroundResource(
            R.drawable.ic_baseline_favorite_24
        )
        else itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24_black)
    }
}
