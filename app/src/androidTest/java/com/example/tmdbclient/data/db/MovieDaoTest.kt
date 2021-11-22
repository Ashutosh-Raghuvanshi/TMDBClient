package com.example.tmdbclient.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.tmdbclient.data.model.movie.Movie
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class MovieDaoTest {
    @get:Rule

    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: MovieDao
    private lateinit var database: TMDBDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TMDBDatabase::class.java
        ).build()

        dao = database.movieDao()
    }

    @Test
    fun saveMovieTest() = runBlocking {
        val movies = listOf(
            Movie(1,"Overview1", "poster1", "date1", "title1"),
            Movie(2,"Overview2", "poster2", "date2", "title2"),
            Movie(3,"Overview3", "poster3", "date3", "title3"),
            Movie(4,"Overview4", "poster4", "date4", "title4")
        )
        dao.saveMovies(movies)

        val result = dao.getMovies()
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result).isEqualTo(movies)
    }

    @Test
    fun deleteMovieTest() = runBlocking {
        val movies = listOf(
            Movie(1,"Overview1", "poster1", "date1", "title1"),
            Movie(2,"Overview2", "poster2", "date2", "title2"),
            Movie(3,"Overview3", "poster3", "date3", "title3"),
            Movie(4,"Overview4", "poster4", "date4", "title4")
        )
        dao.saveMovies(movies)
        dao.deleteAllMovies()

        val result = dao.getMovies()
        Truth.assertThat(result).isEmpty()
    }

    @After
    fun tearDown() {
        database.close()
    }
}