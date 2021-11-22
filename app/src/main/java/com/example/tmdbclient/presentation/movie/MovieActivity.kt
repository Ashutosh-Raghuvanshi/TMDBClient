package com.example.tmdbclient.presentation.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.databinding.ActivityMovieBinding
import com.example.tmdbclient.presentation.di.Injector
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    @Inject
    lateinit var factory: MovieViewModelFactory
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        (application as Injector).createMovieSubComponent()
            .inject(this)

        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter()
        binding.rvMovie.adapter = adapter
        displayPopularMovies()
    }

    private fun displayPopularMovies(){
        binding.progressBarMovie.visibility = View.VISIBLE
        viewModel.getMovies().observe(this){
            showMoviesOnUI(it)
        }
    }

    private fun showMoviesOnUI(it: List<Movie>?) {
        if (it.isNullOrEmpty()) {
            binding.progressBarMovie.visibility = View.GONE
            Toast.makeText(this, "Error in getting movies.", Toast.LENGTH_SHORT).show()
        } else {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            binding.progressBarMovie.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.progressBarMovie.visibility = View.VISIBLE
        return when (item.itemId){
            R.id.action_update -> {
                viewModel.updateMovies().observe(this) {
                    showMoviesOnUI(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}