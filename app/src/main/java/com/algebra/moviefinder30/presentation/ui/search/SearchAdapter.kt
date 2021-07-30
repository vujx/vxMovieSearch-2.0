package com.algebra.moviefinder30.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.SearchMovieItemBinding
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.util.displayPic

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.MovieViewHolder>() {

    val listOfMovies = mutableListOf<Movie>()
    var listOfFavMovies = mutableListOf<FavoriteMovieEntity>()
    var listener: Listener? = null

    fun setMovieList(list: List<Movie>){
        listOfMovies.clear()
        listOfMovies.addAll(list)
        notifyDataSetChanged()
    }

    fun setFavoriteList(list: List<FavoriteMovieEntity>){
        listOfFavMovies.clear()
        listOfFavMovies.addAll(list)
        notifyDataSetChanged()
    }

    private fun checkIsFav(position: Int): Boolean{
        return FavoriteMovieMapper().mapFromEntity(listOfMovies[position]) in listOfFavMovies
    }

    inner class MovieViewHolder(private val itemMovie: SearchMovieItemBinding): RecyclerView.ViewHolder(itemMovie.root){
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(listOfMovies[layoutPosition].imdbId, checkIsFav(layoutPosition))
            }
            itemMovie.ivFavorite.setOnClickListener {
                listener?.onFavClick(listOfMovies[layoutPosition], checkIsFav(layoutPosition))
            }
        }

        fun bind(movie: Movie){
            if(adapterPosition == 0 || movie.year != listOfMovies[adapterPosition - 1].year){
                itemMovie.tvYear.visibility = View.VISIBLE
                itemMovie.tvYear.text = movie.year
            } else itemMovie.tvYear.visibility = View.GONE

            itemMovie.tvTitle.text = movie.title
            displayPic(itemView, movie.pictureURL, itemMovie.imMovie)

            if(FavoriteMovieMapper().mapFromEntity(movie) in listOfFavMovies) itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            else itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24_black)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }

    interface Listener{
        fun onFavClick(movie: Movie, isFav: Boolean)
        fun onItemClick(imdb: String, isFav: Boolean)
    }
}