package com.example.tmdbclient.domain.usecase

import com.example.tmdbclient.domain.repository.MovieRepository

class UpdateMovieUseCase(private var movieRepository: MovieRepository) {
    suspend fun execute() = movieRepository.updateMovies()
}