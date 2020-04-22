package com.raywenderlich.wewatch.details

import com.google.gson.GsonBuilder
import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.data.model.details.MovieDetails
import com.raywenderlich.wewatch.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailsPresenterTests : BaseTest(){
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: DetailsContract.DetailsActivityInterface

    @Mock
    private val mockDataSource = RemoteDataSource()

    lateinit var detailsPresenter: DetailsPresenter


    @Before
    fun setUp() {
        detailsPresenter = DetailsPresenter(view = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testSearchMovie() {
        val myDummyResponse = dummyResponse
        Mockito.doReturn(Observable.just(myDummyResponse)).`when`(mockDataSource).getDetailsObservable(ArgumentMatchers.anyInt())
        detailsPresenter.getDetailsResults(10)
        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMovieError() {
        Mockito.doReturn(Observable.error<Throwable>(Throwable("Something went wrong"))).`when`(mockDataSource).getDetailsObservable(ArgumentMatchers.anyInt())
        detailsPresenter.getDetailsResults(10)
        Mockito.verify(mockActivity).displayError("Error fetching Movie Data")
    }


    private val dummyResponse: MovieDetails
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("movieDetails.json"), MovieDetails::class.java)
        }
}