package com.raywenderlich.wewatch.details

import android.util.Log
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import com.raywenderlich.wewatch.search.SearchContract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailsPresenter constructor(private val view: SearchActivityInterface, private val dataSource: RemoteDataSource) : SearchPresenterInterface {

    private val TAG = "SearchPresenter"
    private val compositeDisposable = CompositeDisposable()

    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    val searchResultsObservable: (String) -> Observable<TmdbResponse> = { query -> dataSource.searchResultsObservable(query) }

    val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {

            override fun onNext(@NonNull tmdbResponse: TmdbResponse) {
                view.displayResult(tmdbResponse)
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