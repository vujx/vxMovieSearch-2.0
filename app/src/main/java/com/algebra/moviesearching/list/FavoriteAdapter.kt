package com.algebra.moviesearching.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviesearching.databinding.ItemFavoriteBinding
import com.algebra.moviesearching.model.FavoriteMovie
import com.bumptech.glide.Glide

class FavoriteAdapter(private val activity: AppCompatActivity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private val listOfFavoriteMovies = mutableListOf<FavoriteMovie>()

    fun setList(list: List<FavoriteMovie>){
        listOfFavoriteMovies.clear()
        listOfFavoriteMovies.addAll(list)
        listOfFavoriteMovies.sortBy {
            it.title.toLowerCase()
        }
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val itemFavorite: ItemFavoriteBinding): RecyclerView.ViewHolder(itemFavorite.root){

        fun bind(favoriteMovie: FavoriteMovie){
            itemFavorite.tvTitle.text = favoriteMovie.title
            itemFavorite.tvYear.text = favoriteMovie.year.toString()
            Glide.with(activity)
                .load(favoriteMovie.picture)
                .into(itemFavorite.imMovie)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfFavoriteMovies.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listOfFavoriteMovies[position])
    }


}