package ui

import Constant.API_KEY
import Constant.BASE_URL
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.MovieListResponse

class MovieListViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = API_KEY, refreshToken = ""
                    )
                }
            }
        }
        install(ContentNegotiation) {
            json()
        }
    }

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
        val movies = httpClient
            .get(BASE_URL + "movie/popular?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
            .body<MovieListResponse>()
        return movies
    }

    override fun onCleared() {
        httpClient.close()
    }
}