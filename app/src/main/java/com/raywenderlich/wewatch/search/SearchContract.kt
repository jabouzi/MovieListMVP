package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.model.TmdbResponse

class SearchContract {
    interface SearchPresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
        fun getView(): SearchActivityInterface
    }

    interface SearchActivityInterface {
        fun displayResult(movies: List<Movie>)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}