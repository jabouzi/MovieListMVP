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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsPresenter constructor(private val view: DetailsContract.DetailsActivityInterface, private val dataSource: RemoteDataSource) :
        DetailsContract.DetailsPresenterInterface, CoroutineScope {

    private val TAG = "SearchPresenter"
    private val parentJob = SupervisorJob()

    override fun getDetailsResults(id: Int) {
        launch {
            val details = dataSource.getDetails(id)
            if (details != null) {
                view.displayResult(details)
            } else {
                view.displayError("Error fetching Movie Data")
            }
        }
    }

    override fun stop() {
//        compositeDisposable.clear()
        parentJob.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

}