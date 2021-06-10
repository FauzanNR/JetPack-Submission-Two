package com.app.jetpacksubmissiontwo.ui.tv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.repository.DataRepository

class TvViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getTvPopular(): MutableLiveData<TvModel> =
        dataRepository.remoteDataTvPopular() as MutableLiveData<TvModel>
}