package com.raywenderlich.wewatch.details

import com.raywenderlich.wewatch.data.model.details.MovieDetails

class DetailsContract {
    interface DetailsPresenterInterface {
        fun getDetailsResults(id: Int)
        fun stop()
    }

    interface DetailsActivityInterface {
        fun displayResult(movieDetails: MovieDetails)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}