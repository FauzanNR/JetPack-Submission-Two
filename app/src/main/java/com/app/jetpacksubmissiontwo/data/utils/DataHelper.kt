package com.app.jetpacksubmissiontwo.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.network.response.FilmResponse
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import com.app.jetpacksubmissiontwo.data.remote.network.response.TvResponse
import retrofit2.Response

class DataHelper {
    //response to live data
    fun fromApiToMovie(responseApi: Response<FilmResponse>): LiveData<FilmModel> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    FilmModel(
                        resultMovieModels = responseApi.body()?.results as List<ResultMovie>
                    )
                )
            EspressoIdlingResource.decrement()
        }
    }

    fun fromApiToTv(responseApi: Response<TvResponse>): LiveData<TvModel> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    TvModel(
                        resultModels = responseApi.body()?.results as List<ResultTv>
                    )
                )
            EspressoIdlingResource.decrement()
        }
    }

    fun fromApiToDetailMovie(responseApi: Response<ResultMovie>): LiveData<ResultMovie> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    responseApi.body() as ResultMovie
                )
            EspressoIdlingResource.decrement()
        }
    }

    fun fromApiToDetailTv(responseApi: Response<ResultTv>): LiveData<ResultTv> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    responseApi.body() as ResultTv
                )
            EspressoIdlingResource.decrement()
        }
    }
}