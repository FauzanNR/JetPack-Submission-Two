package com.app.jetpacksubmissiontwo.data.utils

import androidx.lifecycle.LiveData
import retrofit2.Response

interface ApiMapper<ApiEntity, DomainEntity> {
    fun mapFromApi(responseApi: Response<ApiEntity>): LiveData<DomainEntity>
}