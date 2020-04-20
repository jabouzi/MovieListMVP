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
        addMoviePresenter.addMovie("", "", "")
        Mockito.verify(mockActivity).displayError("Movie title cannot be empty")
    }

    @Test
    fun testAddMovieWithTitle() {
        addMoviePresenter.addMovie("The Lion King", "1994-05-07", "bKPtXn9n4M4s8vvZrbw40mYsefB.jpg")
        Mockito.verify(mockDataSource).insert(captureArg(movieArgumentCaptor))
        assertEquals("The Lion King", movieArgumentCaptor.value.title)
        Mockito.verify(mockActivity).returnToMain()
    }
}