package com.example.flimix_tv.data.model

import com.google.gson.annotations.SerializedName

/**
 * Movie model matching API response.
 */
data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: List<String>,
    val thumbnail: String,
    val cover: String?,
    val description: String,
    @SerializedName("play_url") val playUrl: String,
)

/**
 * Wrapper for GET /api/v1/public/movies/ response.
 */
data class MoviesResponse(
    val movies: List<Movie>,
    @SerializedName("last_updated") val lastUpdated: String? = null,
)
