package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import com.raywenderlich.wewatch.search.SearchContract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter constructor(private val view: SearchActivityInterface, private val dataSource: RemoteDataSource) : SearchPresenterInterface {

    private val TAG = "SearchPresenter"
    private val compositeDisposable = CompositeDisposable()

    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(SearchObserverDelegate(this).observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    val searchResultsObservable: (String) -> Observable<TmdbResponse> = { query -> dataSource.searchResultsObservable(query) }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun getView(): SearchActivityInterface {
        return view
    }

}