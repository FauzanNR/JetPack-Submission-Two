package com.app.jetpacksubmissiontwo.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import com.app.jetpacksubmissiontwo.data.repository.DataRepository

class DetailViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getMovieDetail(id: Int): MutableLiveData<ResultMovie> =
        dataRepository.remoteDataMovieDetail(id) as MutableLiveData<ResultMovie>

    fun getTvDetail(id: Int): MutableLiveData<ResultTv> =
        dataRepository.remoteDataTvDetail(id) as MutableLiveData<ResultTv>
}