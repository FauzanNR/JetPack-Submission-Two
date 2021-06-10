package com.app.jetpacksubmissiontwo.ui.movie


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.remote.RemoteDataSource
import com.app.jetpacksubmissiontwo.data.repository.DataRepository
import com.app.jetpacksubmissiontwo.data.utils.DataHelper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<FilmModel>

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var remoteDataSource: RemoteDataSource

    lateinit var viewModelTest: MovieViewModel

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource.getInstance(DataHelper())
        viewModelTest = MovieViewModel(dataRepository)
    }

    @Test
    fun getMoviePopular() = runBlocking {
        val data = MutableLiveData<FilmModel>()
        data.value = remoteDataSource.getPopularMovie().value

        `when`(dataRepository.remoteDataMoviePopular()).thenReturn(data)
        val test = viewModelTest.getMoviePopular()
        verify(dataRepository, atLeastOnce()).remoteDataMoviePopular()

        assertNotNull(test)
        assertEquals(data.value, test.value)

        viewModelTest.getMoviePopular().observeForever(observer)
        verify(observer).onChanged(remoteDataSource.getPopularMovie().value)
    }
}