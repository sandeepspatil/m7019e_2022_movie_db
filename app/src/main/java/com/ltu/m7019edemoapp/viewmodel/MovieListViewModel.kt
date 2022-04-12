package com.ltu.m7019edemoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.model.Movie

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    init {
        _movies.postValue(Movies().list)
    }
}