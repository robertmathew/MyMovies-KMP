package ui.detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.MovieDetailResponse
import network.NetworkComponent
import utils.Constant

class MovieDetailViewModel : ScreenModel {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        screenModelScope.launch {
            val movie = getMovieDetails(movieId)
            _uiState.update {
                it.copy(movieDetail = movie)
            }
        }
    }

    private suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        val movies = NetworkComponent.httpClient
            .get(Constant.BASE_URL + "movie/" + movieId)
            .body<MovieDetailResponse>()
        return movies
    }
}