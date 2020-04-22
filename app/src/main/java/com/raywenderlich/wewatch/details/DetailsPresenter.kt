package com.raywenderlich.wewatch.details

import android.util.Log
import com.raywenderlich.wewatch.data.model.details.MovieDetails
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import com.raywenderlich.wewatch.search.SearchContract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailsPresenter constructor(private val view: DetailsContract.DetailsActivityInterface, private val dataSource: RemoteDataSource) : DetailsContract.DetailsPresenterInterface {

    private val TAG = "SearchPresenter"
    private val compositeDisposable = CompositeDisposable()

    override fun getDetailsResults(id: Int) {
        val searchResultsDisposable = getDetailsObservable(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    val getDetailsObservable: (Int) -> Observable<MovieDetails> = { id -> dataSource.getDetailsObservable(id) }

    val observer: DisposableObserver<MovieDetails>
        get() = object : DisposableObserver<MovieDetails>() {

            override fun onNext(@NonNull movieDetails: MovieDetails) {
                view.displayResult(movieDetails)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                view.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {}
        }

    override fun stop() {
        compositeDisposable.clear()
    }

}