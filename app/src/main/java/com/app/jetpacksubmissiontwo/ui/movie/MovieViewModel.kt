package com.app.jetpacksubmissiontwo.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.repository.DataRepository

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    fun getMoviePopular(): MutableLiveData<FilmModel> =
        dataRepository.remoteDataMoviePopular() as MutableLiveData<FilmModel>
}