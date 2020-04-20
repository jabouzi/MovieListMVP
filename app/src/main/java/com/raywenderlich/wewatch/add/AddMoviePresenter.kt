package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.add.AddMovieContract.*
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie

class AddMoviePresenter constructor(private val view: AddMovieViewInterface, private val dataSource: LocalDataSource): AddMoviePresenterInterface {
    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            view.displayError("Movie title cannot be empty")
        } else {
            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie = movie)
            view.returnToMain()
        }
    }

}