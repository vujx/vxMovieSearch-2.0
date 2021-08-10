package com.algebra.moviefinder30.domain.util.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.algebra.moviefinder30.data.FakeSearchRepository
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
class SearchUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeRepo: FakeSearchRepository

    @Before
    fun setup() {
        fakeRepo = FakeSearchRepository()
    }

    @Test
    fun `use case which check if search movie is added`() = runBlockingTest {
        fakeRepo.insertSearchMovie(FavoriteUseCaseTest.movie)
        val result = fakeRepo.getSearchMoviesByYear("titanic")

        assertThat(result.contains(FavoriteUseCaseTest.movie))
    }
}
