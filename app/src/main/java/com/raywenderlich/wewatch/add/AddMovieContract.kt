package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.model.Movie

class AddMovieContract {

    interface AddMoviePresenterInterface {
        fun addMovie(movie: Movie)
    }

    interface AddMovieViewInterface {
        fun returnToMain()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}