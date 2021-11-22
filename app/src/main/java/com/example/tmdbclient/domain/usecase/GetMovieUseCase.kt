package com.example.tmdbclient.domain.usecase

import com.example.tmdbclient.domain.repository.MovieRepository

class GetMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute() = movieRepository.getMovies()
}