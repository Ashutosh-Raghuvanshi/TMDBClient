package com.example.tmdbclient.data.repository.movie.dataSourceImpl

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.dataSource.MovieCacheDataSource

class MovieCacheDataSourceImpl: MovieCacheDataSource {
    var cachedMovies = ArrayList<Movie>()
    override suspend fun getMoviesFromCache(): List<Movie> {
        return cachedMovies
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
        cachedMovies.clear()
        cachedMovies = ArrayList(movies)
    }
}