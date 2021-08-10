package com.algebra.moviefinder30.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.algebra.moviefinder30.data.model.local.FavoriteMovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FavoriteMovieDaoTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var database: AppDatabase
    private lateinit var dao: FavoriteMovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.favoriteDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertMovieToFavoriteTest() = runBlockingTest {
        dao.insertFavoriteMovie(favMovie)
        val result = dao.getFavoriteMovie()

        assertThat(result).isEqualTo(favMovie)
    }

    @Test
    fun deleteMovieFromDatabaseTest() = runBlockingTest {
        dao.apply {
            insertFavoriteMovie(favMovie)
            removeFavoriteMovie(favMovie.imdbId)
        }

        val result = dao.getFavoriteMovie()
        assertThat(result).isNull()
    }

    @Test
    fun deleteAllMoviesFromDatabaseTest() = runBlockingTest {
        dao.apply {
            insertFavoriteMovie(favMovie)
            removeAllFavoritesMovie()
        }
        val result = dao.getAllFavoritesMovies()
        assertThat(result).isEmpty()
    }

    companion object {
        val favMovie = FavoriteMovieEntity(
            "tt0120338",
            "Titanic",
            "1997",
            "https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg",
            "tt0120338"
        )
    }
}
