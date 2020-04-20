package com.raywenderlich.wewatch.main

import android.util.Log
import com.raywenderlich.wewatch.main.MainContract.*
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter constructor(private val view: ViewInterface, private val dataSource: LocalDataSource) : PresenterInterface {
    private val TAG = "MainPresenter"

    private val compositeDisposable = CompositeDisposable()
    //1
    val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies

    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
                if (movieList.isEmpty()) {
                    view.displayNoMovies()
                } else {
                    view.displayMovies(movieList)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie list.", e)
                view.displayError("Error fetching movie list")
            }

            override fun onComplete() {
//                Log.d(TAG, "Completed")
            }
        }

    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun onDeleteTapped(selectedMovies: HashSet<Movie>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie)
        }

        if (selectedMovies.size == 1) {
            view.displayMessage("Movie deleted")
        } else if (selectedMovies.size > 1) {
            view.displayMessage("Movies deleted")
        }
    }
}