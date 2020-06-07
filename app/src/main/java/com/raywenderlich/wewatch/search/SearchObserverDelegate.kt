package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.observers.DisposableObserver

class SearchObserverDelegate(private val searchPresenter: SearchContract.SearchPresenterInterface) {

    val observer: DisposableObserver<TmdbResponse?>
        get() = object : DisposableObserver<TmdbResponse?>() {
            override fun onNext(tmdbResponse: TmdbResponse) {
                searchPresenter.getView().displayResult(tmdbResponse)
            }

            override fun onError(e: Throwable) {
                searchPresenter.getView().displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                // won't be implemented
            }
        }
}