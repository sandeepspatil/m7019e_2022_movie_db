package com.ltu.m7019edemoapp.viewmodel

import TMDBApi
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019edemoapp.database.MovieDatabaseDao
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.network.DataFetchStatus
import com.ltu.m7019edemoapp.network.MovieResponse
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _dataFetchStatus: MutableLiveData<DataFetchStatus> = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    private val _navigateToMovieDetail = MutableLiveData<Movie>()
    val navigateToMovieDetail: LiveData<Movie>
        get() {
            return _navigateToMovieDetail
        }

    init {
        _dataFetchStatus.postValue(DataFetchStatus.LOADING)
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val movieResponse: MovieResponse =
                    TMDBApi.movieListRetrofitService.getPopularMovies()
                _movies.postValue(movieResponse.results)
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
                _movies.postValue(listOf())
            }
        }
    }
    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                val movieResponse: MovieResponse =
                    TMDBApi.movieListRetrofitService.getTopRatedMovies()
                _movies.postValue(movieResponse.results)
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
                _movies.postValue(listOf())
            }
        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            _movies.postValue(movieDatabaseDao.getAllMovies())
        }
    }

    fun addMovie() {
        viewModelScope.launch {
            movies.value?.let { movieDatabaseDao.insert(it[0]) }
        }
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }
}