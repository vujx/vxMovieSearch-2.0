package com.algebra.moviesearching.list

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

class MovieAdapter(private val activity: AppCompatActivity, val listOfFav: MutableList<FavoriteMovie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val listOfMovies = mutableListOf<MovieDetails>()
    val listOfYear = mutableMapOf<Int, String>()

    var listener: Listener? = null
    private var yearBefore = ""

    fun setList(list: List<MovieDetails>){
        listOfMovies.clear()
        yearBefore = ""
        listOfMovies.addAll(list)
        listOfMovies.sortBy {
            if(it.year.length > 4)
                it.year = it.year.substring(0, 4)
            val year = try{
                it.year.toInt()
                it.year
            } catch (e: NumberFormatException){
                "1"
            }
            year.toInt()
        }
        setYears()
        notifyDataSetChanged()
    }

    private fun setYears(){
        listOfYear.clear()
        for(i: Int in 0 until listOfMovies.size){
            if(listOfMovies[i].year.length > 4)
                listOfMovies[i].year = listOfMovies[i].year.substring(0, 4)

            if(yearBefore != listOfMovies[i].year) {
                yearBefore = listOfMovies[i].year
                listOfYear[i] = yearBefore
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MovieViewHolder(private val itemMovie: ItemMoviesBinding): RecyclerView.ViewHolder(itemMovie.root){
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(listOfMovies[layoutPosition].imdbId, listOfMovies[layoutPosition].title)
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
            }
        }

        fun bind(movie: MovieDetails){

            var checkIfExistYear = false

            listOfYear.mapKeys {
                if(it.key == layoutPosition) checkIfExistYear = true
            }

            if(checkIfExistYear){
                itemMovie.tvYear.visibility = View.VISIBLE
                itemMovie.tvYear.text = movie.year
            } else itemMovie.tvYear.visibility = View.GONE

            itemMovie.tvTitle.text = movie.title

            if(movie.pictureUrl != "N/A")
                Glide.with(activity)
                    .load(movie.pictureUrl)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.no_image_available)
                    .into(itemMovie.imMovie)
            else itemMovie.imMovie.setBackgroundResource(R.drawable.no_image_available)

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
        fun onItemClick(imdbId: String, title: String)
    }
}