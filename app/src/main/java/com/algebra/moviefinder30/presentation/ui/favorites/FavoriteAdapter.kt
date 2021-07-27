package com.algebra.moviefinder30.presentation.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.algebra.moviefinder30.databinding.FavoriteMovieItemBinding
import com.algebra.moviefinder30.util.displayPic
import com.bumptech.glide.Glide
import java.util.*

class FavoriteAdapter(): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private val listOfFavoriteMovies = mutableListOf<FavoriteMovieEntity>()
    var listener: Listener? = null

    fun setList(list: List<FavoriteMovieEntity>){
        listOfFavoriteMovies.clear()
        listOfFavoriteMovies.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val itemFavorite: FavoriteMovieItemBinding): RecyclerView.ViewHolder(itemFavorite.root){
        init{
            itemFavorite.ivFavorite.setOnClickListener {
                listener?.onFavClick(listOfFavoriteMovies[layoutPosition].imdbId)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(listOfFavoriteMovies[layoutPosition].imdbId, listOfFavoriteMovies[layoutPosition].title)
            }
        }

        fun bind(favoriteMovie: FavoriteMovieEntity){
            itemFavorite.tvTitle.text = favoriteMovie.title
            itemFavorite.tvYear.text = favoriteMovie.year
            displayPic(itemView, favoriteMovie.pictureURL, itemFavorite.imMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfFavoriteMovies.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listOfFavoriteMovies[position])
    }

    interface Listener{
        fun onFavClick(imdbId: String)
        fun onItemClick(imdbId: String, title: String)
    }
}