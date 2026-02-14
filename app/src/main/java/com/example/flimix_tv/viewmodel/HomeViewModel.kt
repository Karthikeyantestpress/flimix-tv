package com.example.flimix_tv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flimix_tv.data.model.Movie
import com.example.flimix_tv.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI state for the home screen (loading / success / error).
 */
sealed class UiState {
    object Loading : UiState()
    data class Success(val movies: List<Movie>) : UiState()
    data class Error(val message: String) : UiState()
}

class HomeViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies(createdAfter: String? = null) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val movies = repository.getMovies(createdAfter = createdAfter)
                _uiState.value = UiState.Success(movies)
            } catch (e: IOException) {
                _uiState.value = UiState.Error(e.message ?: "Network error")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    /** Resolve movie by id from last successful load (for navigation to detail). */
    fun getMovieById(id: Int): Movie? {
        return (_uiState.value as? UiState.Success)?.movies?.find { it.id == id }
    }

    /** Set before navigating to player so player can use this if getMovieById is null (TV back stack). */
    var lastPlayUrl: String? = null
}
