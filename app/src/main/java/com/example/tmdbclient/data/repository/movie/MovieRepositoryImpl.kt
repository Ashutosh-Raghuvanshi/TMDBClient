package com.example.tmdbclient.data.repository.movie

import android.util.Log
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.dataSource.MovieCacheDataSource
import com.example.tmdbclient.data.repository.movie.dataSource.MovieLocalDataSource
import com.example.tmdbclient.data.repository.movie.dataSource.MovieRemoteDataSource
import com.example.tmdbclient.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private var cacheDataSource: MovieCacheDataSource
): MovieRepository {
    override suspend fun getMovies(): List<Movie>? {
        return getMoviesFromCache()
    }

    override suspend fun updateMovies(): List<Movie>? {
        val movieList = getMoviesFromAPI()
        localDataSource.deleteAllMovies()
        localDataSource.saveMoviesToDB(movieList)
        cacheDataSource.saveMoviesToCache(movieList)

        return movieList
    }


    private suspend fun getMoviesFromAPI(): List<Movie> {
        var movieList = listOf<Movie>()

        try {

            val response = remoteDataSource.getMovies()
            val body = response.body()

            if (body != null) {
                movieList = body.movies
            }
        }catch (e: Exception){
            Log.d("Ashutosh", "Getting movie failed remote")
        }

        return movieList
    }

    private suspend fun getMoviesFromDb(): List<Movie> {
        var movieList = listOf<Movie>()

        try {
            movieList = localDataSource.getMoviesFromDB()
        }catch (e: Exception){
            Log.d("Ashutosh", "Getting movie failed local")
        }

        if(movieList.isEmpty()){
            movieList = getMoviesFromAPI()
            localDataSource.saveMoviesToDB(movieList)
        }

        return movieList
    }

    private suspend fun getMoviesFromCache(): List<Movie> {
        var movieList = listOf<Movie>()

        try {
            movieList = cacheDataSource.getMoviesFromCache()
        }catch (e: Exception){
            Log.d("Ashutosh", "Getting movie failed cache")
        }

        if(movieList.isEmpty()){
            movieList = getMoviesFromDb()
            cacheDataSource.saveMoviesToCache(movieList)
        }

        return movieList
    }
}