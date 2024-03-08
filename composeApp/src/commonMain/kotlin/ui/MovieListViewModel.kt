package ui

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.MovieListResponse
import network.NetworkComponent
import utils.Constant.BASE_URL

class MovieListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateImages()
    }

    fun updateImages() {
        viewModelScope.launch {
            val movies = getMovies()
            _uiState.update {
                it.copy(movieList = movies)
            }
        }
    }

    private suspend fun getMovies(): MovieListResponse {
        val movies = NetworkComponent.httpClient
            .get(BASE_URL + "movie/popular?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
            .body<MovieListResponse>()
        return movies
    }
}