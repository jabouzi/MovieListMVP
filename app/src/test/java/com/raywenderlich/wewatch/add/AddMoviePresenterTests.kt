package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity : AddMovieContract.AddMovieViewInterface
    @Mock
    private lateinit var mockDataSource : LocalDataSource
    lateinit var addMoviePresenter : AddMoviePresenter

    @Captor
    private lateinit var movieArgumentCaptor: ArgumentCaptor<Movie>

    @Before
    fun setUp() {
        addMoviePresenter = AddMoviePresenter(view = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testAddMovieNoTitle() {
        addMoviePresenter.addMovie(Movie(voteCount=0, id=0, video=false, voteAverage=8.0F, title="",
                popularity=0F, posterPath="", originalLanguage="", originalTitle="",
                genreIds=listOf<Int>(0, 0, 0), backdropPath="", adult=false, overview="",
                releaseDate="", watched=false))
        Mockito.verify(mockActivity).displayError("Movie title cannot be empty")
    }

    @Test
    fun testAddMovieWithTitle() {
        addMoviePresenter.addMovie(Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Title1",
                popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath1", adult=false, overview="Overview1",
                releaseDate="ReleaseDate1", watched=false))
        Mockito.verify(mockDataSource).insert(captureArg(movieArgumentCaptor))
        assertEquals("The Lion King", movieArgumentCaptor.value.title)
        Mockito.verify(mockActivity).returnToMain()
    }
}