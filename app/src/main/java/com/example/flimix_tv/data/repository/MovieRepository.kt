package com.example.flimix_tv.data.repository

import com.example.flimix_tv.data.model.Movie
import com.example.flimix_tv.data.remote.MovieApi
import com.example.flimix_tv.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Repository for movies. Phase 1: getMovies() with no param.
 * Phase 2: getMovies(createdAfter = "2026-02-14") when ready.
 */
class MovieRepository(
    private val api: MovieApi = RetrofitClient.movieApi,
) {
    suspend fun getMovies(createdAfter: String? = null): List<Movie> = withContext(Dispatchers.IO) {
        try {
            val response = api.getMovies(createdAfter = createdAfter)
            response.movies
        } catch (e: IOException) {
            throw e
        }
    }
}
