package com.algebra.moviefinder30.presentation.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.FavoriteMovieItemBinding
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(private val listener: FavoriteAdapterListener) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val listOfFavoriteMovies = mutableListOf<FavoriteMovieEntity>()

    fun setList(list: List<FavoriteMovieEntity>) {
        listOfFavoriteMovies.clear()
        listOfFavoriteMovies.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, listener, listOfFavoriteMovies)
    }

    override fun getItemCount(): Int = listOfFavoriteMovies.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listOfFavoriteMovies[position])
    }
}
