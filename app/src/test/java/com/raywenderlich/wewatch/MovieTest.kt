package com.raywenderlich.wewatch

import com.raywenderlich.wewatch.model.Movie
import junit.framework.Assert
import org.junit.Test

class MovieTest {
    @Test
    fun testGetReleaeYearFromStringFormattedDate() {
        val movie = Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Finding Nemo",
                popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath1", adult=false, overview="Overview1",
                releaseDate="2003", watched=false)
        Assert.assertEquals("2003", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromYear() {
        val movie = Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Finding Nemo",
                popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                genreIds=listOf<Int>(28, 878, 53), backdropPath="PosterPath1", adult=false, overview="Overview1",
                releaseDate="2003", watched=false)
        Assert.assertEquals("2003", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseEmpty() {
        val movie = Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Finding Nemo",
                popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                genreIds=listOf<Int>(28, 878, 53), backdropPath="", adult=false, overview="Overview1",
                releaseDate="", watched=false)
        Assert.assertEquals("", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseNull() {
        val movie = Movie(voteCount=7868, id=280, video=false, voteAverage=8.0F, title="Finding Nemo",
                popularity=31.613F, posterPath="PosterPath1", originalLanguage="en", originalTitle="Title1",
                genreIds=listOf<Int>(28, 878, 53), backdropPath="", adult=false, overview="Overview1",
                releaseDate="", watched=false)
        Assert.assertEquals("", movie.releaseDate)
    }
}
