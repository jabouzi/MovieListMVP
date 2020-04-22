package com.raywenderlich.wewatch.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.raywenderlich.wewatch.BuildConfig
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.data.model.details.MovieDetails
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), DetailsContract.DetailsActivityInterface {

    private lateinit var presenter: DetailsPresenter
    var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieId = intent.extras.getInt("id")
        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        showLoading()
        presenter.getDetailsResults(movieId)
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        presenter = DetailsPresenter(this, dataSource)
    }


    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun displayResult(movieDetails: MovieDetails) {
        Log.e("movieDetails", "$movieDetails")
        hideLoading()
        movieDetails?.let {
            if (it.posterPath != null)
                Picasso.get().load(BuildConfig.TMDB_IMAGEURL + it.posterPath).into(movieImageView)
            else {
                movieImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_local_movies_gray, null))
            }
            movieTitleTextView.setText(movieDetails.title)
            movieReleaseDateTextView.setText(movieDetails.releaseDate)
            movieDetails.voteAverage?.let { it -> movieReviewsTextView.setText(it.toString()) }
            movieoverviewTextView.setText(movieDetails.overview)
        }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MovieDetailsActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayError(string: String) {
        hideLoading()
        displayMessage(string)
    }
}
