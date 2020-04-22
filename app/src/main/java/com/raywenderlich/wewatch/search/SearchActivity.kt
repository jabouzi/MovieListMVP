/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.wewatch.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import com.raywenderlich.wewatch.search.SearchContract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchActivity : AppCompatActivity(), SearchActivityInterface {
  private val TAG = "SearchActivity"
  private lateinit var searchResultsRecyclerView: RecyclerView
  private lateinit var adapter: SearchAdapter
  private lateinit var noMoviesTextView: TextView
  private lateinit var progressBar: ProgressBar
  private lateinit var query: String
  private lateinit var presenter: SearchPresenterInterface

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_movie)
    searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview)
    noMoviesTextView = findViewById(R.id.no_movies_textview)
    progressBar = findViewById(R.id.progress_bar)


    val intent = intent
    query = intent.getStringExtra(SEARCH_QUERY)

    setupPresenter()
    setupViews()
  }

  override fun onStart() {
    super.onStart()
    progressBar.visibility = VISIBLE
    presenter.getSearchResults(query)
  }

  override fun onStop() {
    super.onStop()
    presenter.stop()
  }

  private fun setupPresenter() {
    val dataSource = RemoteDataSource()
    presenter = SearchPresenter(this, dataSource)
  }

  private fun setupViews() {
    searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
  }

  override fun displayResult(tmdbResponse: TmdbResponse) {
    progressBar.visibility = INVISIBLE

    if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
      searchResultsRecyclerView.visibility = INVISIBLE
      noMoviesTextView.visibility = VISIBLE
    } else {
      adapter = SearchAdapter(tmdbResponse.results
              ?: arrayListOf(), this@SearchActivity, itemListener)
      searchResultsRecyclerView.adapter = adapter

      searchResultsRecyclerView.visibility = VISIBLE
      noMoviesTextView.visibility = INVISIBLE
    }
  }

  override fun displayMessage(message: String) {
    Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
  }

  override fun displayError(string: String) {
    displayMessage(string)
  }

  companion object {

    val SEARCH_QUERY = "searchQuery"
    val EXTRA_MOVIE = "SearchActivity.TITLE_REPLY"
  }

  /**
   * Listener for clicks on tasks in the ListView.
   */
  internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
    override fun onItemClick(view: View, position: Int) {
      val movie = adapter.getItemAtPosition(position)

      val replyIntent = Intent()
      replyIntent.putExtra(EXTRA_MOVIE, movie)
      setResult(Activity.RESULT_OK, replyIntent)

      finish()
    }
  }

  interface RecyclerItemListener {
    fun onItemClick(v: View, position: Int)
  }

}

