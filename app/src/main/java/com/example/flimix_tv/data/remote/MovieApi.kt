package com.example.flimix_tv.data.remote

import com.example.flimix_tv.config.ApiEndpoints
import com.example.flimix_tv.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    /**
     * Fetches published movies. Optional created_after for server-side filtering (Phase 2).
     */
    @GET(ApiEndpoints.MOVIES)
    suspend fun getMovies(
        @Query("created_after") createdAfter: String? = null,
    ): MoviesResponse
}
