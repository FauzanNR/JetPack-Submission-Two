package com.app.jetpacksubmissiontwo.data.remote

import androidx.lifecycle.LiveData
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.network.ApiConf
import com.app.jetpacksubmissiontwo.data.remote.network.ApiService
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import com.app.jetpacksubmissiontwo.data.utils.DataHelper

class RemoteDataSource private constructor(private val dataHelper: DataHelper) {

    private val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)

    companion object {
        @Volatile
        private var instances: RemoteDataSource? = null
        fun getInstance(helper: DataHelper): RemoteDataSource =
            instances ?: synchronized(this) {
                instances ?: RemoteDataSource(helper).apply { instances = this }
            }

    }

    suspend fun getPopularMovie(): LiveData<FilmModel> =
        dataHelper.fromApiToMovie(retrofit.getPopularMovie())

    suspend fun getPopularTv(): LiveData<TvModel> = dataHelper.fromApiToTv(retrofit.getPopularTv())
    suspend fun getDetailMovie(id: Int): LiveData<ResultMovie> =
        dataHelper.fromApiToDetailMovie(retrofit.getDetailMovie(movieId = id))

    suspend fun getDetailTv(id: Int): LiveData<ResultTv> =
        dataHelper.fromApiToDetailTv(retrofit.getDetailTv(tvId = id))

}