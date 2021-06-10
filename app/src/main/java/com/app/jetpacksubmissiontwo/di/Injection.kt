package com.app.jetpacksubmissiontwo.di

import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.repository.DataRepository
import com.app.jetpacksubmissiontwo.data.utils.DataHelper

object Injection {
    fun provideRepository(): DataRepository {
        val remoteDataSource = RemoteDataSource.getInstance(DataHelper())
        return DataRepository.getInstance(remoteDataSource)
    }
}