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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchPresenter constructor(private val view: SearchActivityInterface, private val dataSource: RemoteDataSource) :
        SearchPresenterInterface, CoroutineScope {

    private val TAG = "SearchPresenter"
    private val parentJob = SupervisorJob()

    override fun getSearchResults(query: String) {
        launch{
            val movies = dataSource.searchResults(query)
            if (movies != null && movies.isNotEmpty()) {
                view.displayResult(movies)
            } else  {
                view.displayError("Error fetching Movie Data")
            }
        }
    }

    override fun stop() {
        parentJob.cancel()
    }

    override fun getView(): SearchActivityInterface {
        return view
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

}