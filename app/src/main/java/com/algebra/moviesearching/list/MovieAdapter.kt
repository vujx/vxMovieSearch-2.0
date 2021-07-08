package com.algebra.moviesearching.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.algebra.moviesearching.R
import com.algebra.moviesearching.databinding.ItemMoviesBinding
import com.algebra.moviesearching.model.FavoriteMovie
import com.algebra.moviesearching.model.MovieDetails
import com.bumptech.glide.Glide

class MovieAdapter(private val activity: AppCompatActivity, private val listOfFav: MutableList<FavoriteMovie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listOfMovies = mutableListOf<MovieDetails>()
    var listener: Listener? = null
    private var yearBefore = ""

    fun setList(list: List<MovieDetails>){
        listOfMovies.clear()
        listOfMovies.addAll(list)
        listOfMovies.sortBy {
            it.year
        }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val itemMovie: ItemMoviesBinding): RecyclerView.ViewHolder(itemMovie.root){
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(listOfMovies[layoutPosition].imdbId)
            }

            itemMovie.ivFavorite.setOnClickListener {
                var checkIfExist = false
                val movie = listOfMovies[layoutPosition]
                for(i: Int in 0 until listOfFav.size){
                    if(listOfFav[i].imdbId == movie.imdbId){
                        checkIfExist = true
                        listOfFav.removeAt(i)
                        break
                    }
                }

                if(!checkIfExist){
                    itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    listener?.onFavClick(FavoriteMovie(0, movie.title, movie.year, movie.pictureUrl, movie.imdbId), false)
                    listOfFav.add(FavoriteMovie(0, movie.title, movie.year, movie.pictureUrl, movie.imdbId))
                } else {
                    itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    listener?.onFavClick(FavoriteMovie(0, movie.title, movie.year, movie.pictureUrl, movie.imdbId), true)
                }
                Log.d("Ispisovo", checkIfExist.toString())
            }
        }

        fun bind(movie: MovieDetails){
            if(movie.year.length > 4)
                movie.year = movie.year.substring(0, 4)
            if(movie.year == listOfMovies[layoutPosition].year && yearBefore != movie.year) {
                itemMovie.tvYear.text = movie.year
                yearBefore = movie.year
            } else itemMovie.tvYear.visibility = View.GONE

            itemMovie.tvTitle.text = movie.title
            Glide.with(activity)
                .load(movie.pictureUrl)
                .placeholder(R.drawable.no_image_available)
                .into(itemMovie.imMovie)
            var checkIfExist = false
            listOfFav.forEach {
                if(movie.imdbId == it.imdbId) checkIfExist = true
            }
            if(checkIfExist) itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            else itemMovie.ivFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }

    interface Listener{
        fun onFavClick(favMovie: FavoriteMovie, isFav: Boolean)
        fun onItemClick(imdbId: String)
    }
}