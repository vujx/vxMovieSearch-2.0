package com.algebra.moviefinder30.domain.util.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.algebra.moviefinder30.data.FakeFavoriteRepository
import com.algebra.moviefinder30.domain.db.FavoriteMovieMapper
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FavoriteUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeRepo: FakeFavoriteRepository

    @Before
    fun setup() {
        fakeRepo = FakeFavoriteRepository()
    }

    @Test
    fun `use case which check if favorite movie is added`() = runBlockingTest {
        fakeRepo.insertFavoriteMovie(movie)
        val listOfFavMovies = fakeRepo.getAllFavoritesMovies()

        assertThat(listOfFavMovies.contains(FavoriteMovieMapper().mapFromEntity(movie)))
    }

    @Test
    fun `use case which check if favorite movie is removed`() = runBlockingTest {
        fakeRepo.apply {
            insertFavoriteMovie(movie)
            removeFavoriteMovie(movie.imdbId)
        }
        val listOfFavMovies = fakeRepo.getAllFavoritesMovies()

        assertThat(!listOfFavMovies.contains(FavoriteMovieMapper().mapFromEntity(movie)))
    }

    @Test
    fun `use case which check if all favorite movies are removed`() = runBlockingTest {
        fakeRepo.apply {
            insertFavoriteMovie(movie)
            removeAllFavoritesMovie()
        }
        val listOfFavMovies = fakeRepo.getAllFavoritesMovies()

        assertThat(listOfFavMovies.isEmpty())
    }

    companion object {
        val movie = Movie(
            "tt0120338",
            "Titanic",
            "1997",
            "https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg"
        )
    }
}
