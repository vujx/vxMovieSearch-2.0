package com.algebra.moviesearching.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviesearching.R
import com.algebra.moviesearching.databinding.ItemFavoriteBinding
import com.algebra.moviesearching.model.FavoriteMovie
import com.bumptech.glide.Glide
import java.util.*

class FavoriteAdapter(private val activity: AppCompatActivity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private val listOfFavoriteMovies = mutableListOf<FavoriteMovie>()
    var listener: Listener? = null

    fun setList(list: List<FavoriteMovie>){
        listOfFavoriteMovies.clear()
        listOfFavoriteMovies.addAll(list)
        listOfFavoriteMovies.sortBy {
            it.title.toLowerCase(Locale.ROOT)
        }
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val itemFavorite: ItemFavoriteBinding): RecyclerView.ViewHolder(itemFavorite.root){
        init{
            itemFavorite.ivFavorite.setOnClickListener {
                listener?.onFavClick(listOfFavoriteMovies[layoutPosition].imdbId)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(listOfFavoriteMovies[layoutPosition].imdbId)
            }
        }

        fun bind(favoriteMovie: FavoriteMovie){
            itemFavorite.tvTitle.text = favoriteMovie.title
            if(favoriteMovie.year.length == 4)
            itemFavorite.tvYear.text = favoriteMovie.year
            else if(favoriteMovie.year.length == 5) itemFavorite.tvYear.text = favoriteMovie.year.substring(0, 4)
            if(favoriteMovie.picture != "N/A")
            Glide.with(activity)
                .load(favoriteMovie.picture)
                .skipMemoryCache(false)
                .placeholder(R.drawable.no_image_available)
                .into(itemFavorite.imMovie)
            else
                itemFavorite.imMovie.setBackgroundResource(R.drawable.no_image_available)
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

    interface Listener{
        fun onFavClick(imdbId: String)
        fun onItemClick(imdbId: String)
    }
}