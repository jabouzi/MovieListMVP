package com.raywenderlich.wewatch.model.main

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.main.MainContract
import com.raywenderlich.wewatch.main.MainPresenter
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.ArrayList
import kotlin.collections.HashSet


@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests : BaseTest() {
    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity : MainContract.ViewInterface

    @Mock
    private lateinit var mockDataSource : LocalDataSource

    lateinit var mainPresenter : MainPresenter

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(view = mockActivity, dataSource = mockDataSource)
    }

    /**
    Tests for getting movies
     */

    @Test
    fun testGetMyMoviesList() {
        //Set up
        val myDummyMovies = dummyAllMovies
        Mockito.doReturn(Observable.just(myDummyMovies)).`when`(mockDataSource).allMovies

        //Invoke
        mainPresenter.getMyMoviesList()

        //Assert
        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayMovies(myDummyMovies)
    }

    @Test
    fun testGetMyMoviesListNoMovies() {
        Mockito.doReturn(Observable.just(ArrayList<Movie>())).`when`(mockDataSource).allMovies

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayNoMovies()

    }

    @Test
    fun testDeleteSingle() {
        val myDeletedHashSet = deletedHashSetSingle
        mainPresenter.onDeleteTapped(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDataSource).delete(movie)
        }

        Mockito.verify(mockActivity).displayMessage("Movie deleted")
    }

    @Test
    fun testDeleteMultiple() {
        val myDeletedHashSet = deletedHashSetMultiple
        mainPresenter.onDeleteTapped(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDataSource).delete(movie)
        }

        Mockito.verify(mockActivity).displayMessage("Movies deleted")
    }

    private val dummyAllMovies: ArrayList<Movie>
        get() {
            val dummyMovieList = ArrayList<Movie>()
            dummyMovieList.add(Movie("Title1", "ReleaseDate1", "PosterPath1"))
            dummyMovieList.add(Movie("Title2", "ReleaseDate2", "PosterPath2"))
            dummyMovieList.add(Movie("Title3", "ReleaseDate3", "PosterPath3"))
            dummyMovieList.add(Movie("Title4", "ReleaseDate4", "PosterPath4"))
            return dummyMovieList
        }

    private val deletedHashSetSingle: HashSet<Movie>
        get() {
            val deleteHashSet = HashSet<Movie>()
            deleteHashSet.add(dummyAllMovies.get(2))

            return deleteHashSet
        }

    private val deletedHashSetMultiple: HashSet<Movie>
    get() {
        val deletedHashSet = HashSet<Movie>()
        deletedHashSet.add(dummyAllMovies[1])
        deletedHashSet.add(dummyAllMovies[3])
        return deletedHashSet
    }

}