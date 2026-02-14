package com.example.flimix_tv.data.repository

import android.content.Context
import com.example.flimix_tv.data.model.Movie
import com.example.flimix_tv.data.model.MoviesResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Repository for movies. Uses local movies.json from assets (no API) for fast loading.
 */
class MovieRepository(
    private val context: Context,
) {
    private val gson = Gson()

    suspend fun getMovies(createdAfter: String? = null): List<Movie> = withContext(Dispatchers.IO) {
        try {
            context.assets.open("movies.json").reader().use { reader ->
                val response = gson.fromJson(reader.readText(), MoviesResponse::class.java)
                response.movies
            }
        } catch (e: IOException) {
            throw e
        }
    }
}
