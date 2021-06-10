package com.app.jetpacksubmissiontwo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import com.app.jetpacksubmissiontwo.data.utils.DataHelper
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

//@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val id = 10


    private var remoteDataMock = mock(RemoteDataSource::class.java)


    private lateinit var dataRepository: FakeDataRepository
    private lateinit var dataResponse: FakeDataRepository

    @Before
    fun setUp() {
        val dataSource = RemoteDataSource.getInstance(DataHelper())
        dataRepository = FakeDataRepository(remoteDataMock)
        dataResponse = FakeDataRepository(dataSource)
    }

    @Test
    fun testRemoteDataMoviePopular() = runBlocking {
        val data = MutableLiveData<FilmModel>()
        data.value = dataResponse.remoteDataMoviePopular().value
        print("remot MOVIE"+data.value+"\n")
        Mockito.`when`(remoteDataMock.getPopularMovie()).thenReturn(data)

        val test = dataRepository.remoteDataMoviePopular()
        Mockito.verify(remoteDataMock, Mockito.atLeastOnce()).getPopularMovie()

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.value, test.value)
    }

    @Test
    fun testRemoteDataTvPopular() = runBlocking {
        val data = MutableLiveData<TvModel>()
        data.value = dataResponse.remoteDataTvPopular().value

        Mockito.`when`(remoteDataMock.getPopularTv()).thenReturn(data)

        val test = dataRepository.remoteDataTvPopular()
        Mockito.verify(remoteDataMock, Mockito.atLeastOnce()).getPopularTv()

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.value, test.value)
    }

    @Test
    fun testRemoteDataMovieDetail() = runBlocking {
        val data = MutableLiveData<ResultMovie>()
        data.value = dataResponse.remoteDataMovieDetail(id).value

        Mockito.`when`(remoteDataMock.getDetailMovie(id)).thenReturn(data)

        val test = dataRepository.remoteDataMovieDetail(id)
        Mockito.verify(remoteDataMock, Mockito.atLeastOnce()).getDetailMovie(id)

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.value, test.value)
    }

    @Test
    fun testRemoteDataTvDetail() = runBlocking {
        val data = MutableLiveData<ResultTv>()
        data.value = dataResponse.remoteDataTvDetail(id).value

        Mockito.`when`(remoteDataMock.getDetailTv(id)).thenReturn(data)

        val test = dataRepository.remoteDataTvDetail(id)
        Mockito.verify(remoteDataMock, Mockito.atLeastOnce()).getDetailTv(id)

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.value, test.value)
    }
}