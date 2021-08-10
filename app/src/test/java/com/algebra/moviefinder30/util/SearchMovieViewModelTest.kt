package com.algebra.moviefinder30.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.algebra.moviefinder30.MainCoroutineRule
import com.algebra.moviefinder30.data.usecase.UseCaseDbFavorite
import com.algebra.moviefinder30.data.usecase.UseCaseNetwork
import com.algebra.moviefinder30.domain.model.remote.Movie
import com.algebra.moviefinder30.presentation.viewmodel.SearchMoviesViewModel
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchMovieViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var useCaseNetwork: UseCaseNetwork

    @Mock
    private lateinit var useCaseDbFavorite: UseCaseDbFavorite

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchMoviesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchMoviesViewModel(useCaseDbFavorite, useCaseNetwork)
    }

    // -> fix returns ResultOf.Loading but have to return ResultOf.Failure
    @Test
    fun checkIfFetchMovies() {
        runBlockingTest {
            viewModel.fetchMovies("titanic")
            val result = viewModel.movies.getOrAwaitValueTest(10, TimeUnit.SECONDS, {})

            assertThat(result).isInstanceOf(ResultOf.Loading<List<Movie>>().javaClass)
            assertThat(result).isInstanceOf(ResultOf.Failure("", NullPointerException()).javaClass)
        }
    }
}
