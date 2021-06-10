package com.app.jetpacksubmissiontwo.data.repository

import androidx.lifecycle.LiveData
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import kotlinx.coroutines.runBlocking

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource
) {
    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(remoteDataSource: RemoteDataSource): DataRepository =
            instance ?: synchronized(DataRepository::class.java) {
                DataRepository(remoteDataSource).apply { instance = this }
            }
    }

    fun remoteDataMoviePopular(): LiveData<FilmModel> =
        runBlocking { remoteDataSource.getPopularMovie() }

    fun remoteDataTvPopular(): LiveData<TvModel> = runBlocking { remoteDataSource.getPopularTv() }
    fun remoteDataMovieDetail(id: Int): LiveData<ResultMovie> =
        runBlocking { remoteDataSource.getDetailMovie(id) }

    fun remoteDataTvDetail(id: Int): LiveData<ResultTv> =
        runBlocking { remoteDataSource.getDetailTv(id) }
}
