package com.algebra.moviefinder30.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.SearchMovieItemBinding
import com.algebra.moviefinder30.domain.model.remote.Movie
import javax.inject.Inject

class SearchAdapter @Inject constructor(private val listener: SearchAdapterListener) : RecyclerView.Adapter<SearchMovieViewHolder>() {

    private val listOfMovies = mutableListOf<Movie>()
    private var listOfFavMovies = mutableListOf<FavoriteMovieEntity>()

    fun setMovieList(list: List<Movie>) {
        listOfMovies.clear()
        listOfMovies.addAll(list)
        notifyDataSetChanged()
    }

    fun setFavoriteList(list: List<FavoriteMovieEntity>) {
        listOfFavMovies.clear()
        listOfFavMovies.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val binding = SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMovieViewHolder(binding, listener, listOfMovies, listOfFavMovies)
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }
}
