package com.app.jetpacksubmissiontwo.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.repository.DataRepository
import com.app.jetpacksubmissiontwo.data.utils.DataHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<TvModel>

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var remoteDataSource: RemoteDataSource

    lateinit var viewModelTest: TvViewModel

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource.getInstance(DataHelper())
        viewModelTest = TvViewModel(dataRepository)
    }


    @Test
    fun testGetTvPopular() = runBlocking {
        val data = MutableLiveData<TvModel>()
        data.value = remoteDataSource.getPopularTv().value

        Mockito.`when`(dataRepository.remoteDataTvPopular()).thenReturn(data)
        val test = viewModelTest.getTvPopular()
        Mockito.verify(dataRepository, Mockito.atLeastOnce()).remoteDataTvPopular()

        assertNotNull(test)
        assertEquals(data.value, test.value)

        viewModelTest.getTvPopular().observeForever(observer)
        Mockito.verify(observer).onChanged(remoteDataSource.getPopularTv().value)
    }
}