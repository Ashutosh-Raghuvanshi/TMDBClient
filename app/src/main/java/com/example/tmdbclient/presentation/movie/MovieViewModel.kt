package com.example.tmdbclient.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tmdbclient.domain.usecase.GetMovieUseCase
import com.example.tmdbclient.domain.usecase.UpdateMovieUseCase

class MovieViewModel(
    val getMovieUseCase: GetMovieUseCase,
    val updateMovieUseCase: UpdateMovieUseCase
) : ViewModel() {

    fun getMovies() = liveData {
        val movies = getMovieUseCase.execute()
        emit(movies)
    }

    fun updateMovies() = liveData {
        val movies = updateMovieUseCase.execute()
        emit(movies)
    }
}