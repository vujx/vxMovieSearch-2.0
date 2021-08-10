package com.algebra.moviefinder30.data

import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.domain.model.remote.MovieDetails
import com.algebra.moviefinder30.domain.repository.network.MovieNetworkDataSource

class FakeMovieNetworkRepository : MovieNetworkDataSource {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getMoviesByYear(searchValue: String): List<Movie>? {
        return if (shouldReturnNetworkError) null
        else listOf(movie)
    }

    override suspend fun getMoviesDetailsById(imdbId: String): MovieDetails? {
        return if (shouldReturnNetworkError) null
        else movieDetails
    }

    companion object {
        val movieDetails = MovieDetails(
            "Titanic",
            "1997",
            "https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg",
            "tt0120338",
            "1997",
            "194 min",
            "7.8",
            "A seventeen-year-old aristocrat falls in love with kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
            "Drama, Romance"
        )

        val movie = Movie(
            "Titanic",
            "1997",
            "https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg",
            "tt0120338"
        )
    }
}
