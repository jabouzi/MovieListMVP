package com.raywenderlich.wewatch

import com.raywenderlich.wewatch.model.Movie
import junit.framework.Assert
import org.junit.Test

class MovieTest {
    @Test
    fun testGetReleaeYearFromStringFormattedDate() {
        val movie = Movie("Finding Nemo", "2003", "PosterPath1")
        Assert.assertEquals("2003", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromYear() {
        val movie = Movie("Finding Nemo", "2003", "PosterPath1")
        Assert.assertEquals("2003", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseEmpty() {
        val movie = Movie("Finding Nemo", "", "")
        Assert.assertEquals("", movie.releaseDate)
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseNull() {
        val movie = Movie("Finding Nemo", "", "")
        Assert.assertEquals("", movie.releaseDate)
    }
}
