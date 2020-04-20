package com.raywenderlich.wewatch.add

class AddMovieContract {

    interface AddMoviePresenterInterface {
        fun addMovie(title: String, releaseDate: String, posterPath: String)
    }

    interface AddMovieViewInterface {
        fun returnToMain()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}