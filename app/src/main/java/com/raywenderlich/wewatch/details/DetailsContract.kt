package com.raywenderlich.wewatch.details

import com.raywenderlich.wewatch.model.TmdbResponse

class DetailsContract {
    interface SearchPresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }

    interface SearchActivityInterface {
        fun displayResult(tmdbResponse: TmdbResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}