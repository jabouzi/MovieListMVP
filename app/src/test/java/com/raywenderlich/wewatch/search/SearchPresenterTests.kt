package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity : SearchContract.SearchActivityInterface

    @Mock
    private val mockDataSource = RemoteDataSource()

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        searchPresenter = SearchPresenter(view = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testSearchMovie() {
        val myDummyResponse = dummyResponse
        Mockito.doReturn(Observable.just(myDummyResponse)).`when`(mockDataSource).searchResultsObservable(anyString())

        searchPresenter.getSearchResults("The Lion King")

        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMovieError() {
        Mockito.doReturn(Observable.error<Throwable>(Throwable("Something went wrong"))).`when`(mockDataSource).searchResultsObservable(anyString())

        searchPresenter.getSearchResults("The Lion King")

        Mockito.verify(mockActivity).displayError("Error fetching Movie Data")
    }

    private val dummyResponse: TmdbResponse
        get() {
            val dummyMovieList = ArrayList<Movie>()
            dummyMovieList.add(Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Title1",
                    popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                    genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath1", adult=false, overview="Overview1",
                    releaseDate="ReleaseDate1", watched=false))
            dummyMovieList.add(Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Title2",
                    popularity=31.613F, posterPath="PosterPath2", originalLanguage="en", originalTitle="Title2",
                    genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath2", adult=false, overview="Overview2",
                    releaseDate="ReleaseDate2", watched=false))
            dummyMovieList.add(Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Title3",
                    popularity=31.613F, posterPath="PosterPath3", originalLanguage="en", originalTitle="Title3",
                    genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath3", adult=false, overview="Overview3",
                    releaseDate="ReleaseDate3", watched=false))
            dummyMovieList.add(Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Title4",
                    popularity=31.613F, posterPath="PosterPath4", originalLanguage="en", originalTitle="Title4",
                    genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath4", adult=false, overview="Overview4",
                    releaseDate="ReleaseDate4", watched=false))

            return TmdbResponse(1, 4, 5, dummyMovieList)
        }
}