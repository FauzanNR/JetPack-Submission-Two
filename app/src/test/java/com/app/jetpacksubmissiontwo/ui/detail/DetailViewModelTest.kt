package com.app.jetpacksubmissiontwo.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
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
class DetailViewModelTest {
    val id = 10

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerMovie: Observer<ResultMovie>

    @Mock
    lateinit var observerTv: Observer<ResultTv>

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var remoteDataSource: RemoteDataSource

    lateinit var viewModelTest: DetailViewModel

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource.getInstance(DataHelper())
        viewModelTest = DetailViewModel(dataRepository)
    }

    @Test
    fun testGetMovieDetail() = runBlocking {
        val data = MutableLiveData<ResultMovie>()
        data.value = remoteDataSource.getDetailMovie(id).value
        println(remoteDataSource.getDetailMovie(id).value)
        Mockito.`when`(dataRepository.remoteDataMovieDetail(id)).thenReturn(data)
        val test = viewModelTest.getMovieDetail(id)
        Mockito.verify(dataRepository, Mockito.atLeastOnce()).remoteDataMovieDetail(id)

        assertNotNull(test)
        assertEquals(data.value, test.value)

        viewModelTest.getMovieDetail(id).observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(remoteDataSource.getDetailMovie(id).value)
    }

    @Test
    fun testGetTvDetail() = runBlocking {
        val data = MutableLiveData<ResultTv>()
        data.value = remoteDataSource.getDetailTv(id).value

        Mockito.`when`(dataRepository.remoteDataTvDetail(id)).thenReturn(data)
        val test = viewModelTest.getTvDetail(id)
        Mockito.verify(dataRepository, Mockito.atLeastOnce()).remoteDataTvDetail(id)

        assertNotNull(test)
        assertEquals(data.value, test.value)

        viewModelTest.getTvDetail(id).observeForever(observerTv)
        Mockito.verify(observerTv).onChanged(remoteDataSource.getDetailTv(id).value)
    }
}