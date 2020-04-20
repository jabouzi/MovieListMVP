package com.raywenderlich.wewatch.main

import com.raywenderlich.wewatch.model.Movie

class MainContract {
    interface PresenterInterface {
        fun getMyMoviesList()
        fun onStop()
        fun onDeleteTapped(selectedMovies: HashSet<Movie>)
    }

    interface ViewInterface {
        fun displayMovies(movieList: List<Movie>)
        fun displayNoMovies()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}
