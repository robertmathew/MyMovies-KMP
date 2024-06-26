package ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import mymovies.composeapp.generated.resources.Res
import mymovies.composeapp.generated.resources.description
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import utils.Constant


data class MovieDetailScreen(val movieId: Int, val movieName: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel { MovieDetailViewModel() }

        screenModel.getMovieDetail(movieId)

        Scaffold(topBar = {
            ShowDetailsAppBar(title = movieName, onNavigateUp = { navigator.pop() })
        }) { innerPaddingValues ->
            MovieDetail(viewModel = screenModel, modifier = Modifier.padding(innerPaddingValues))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDetailsAppBar(
    title: String?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            if (title != null) {
                Text(text = title)
            }
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable { onNavigateUp() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
        ),

        modifier = modifier,
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MovieDetail(
    viewModel: MovieDetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(uiState.movieDetail != null) {
        LazyColumn(modifier = modifier) {
            item {
                KamelImage(
                    asyncPainterResource(data = Constant.w500ImageUrl + uiState.movieDetail?.backdropPath),
                    contentDescription = uiState.movieDetail?.title,
                    modifier = Modifier.height(200.dp),
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    KamelImage(
                        asyncPainterResource(data = Constant.w500ImageUrl + uiState.movieDetail?.posterPath),
                        contentDescription = uiState.movieDetail?.title,
                        modifier = Modifier.weight(weight = 1f, fill = true)
                    )
                    Column(
                        modifier = Modifier.weight(weight = 2f, fill = true).align(Alignment.Top),
                    ) {
                        Text(
                            text = "Rating",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = uiState.movieDetail?.voteAverage.toString() + "/10",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        )
                        uiState.movieDetail?.releaseDate?.let {
                            Text(
                                text = "Release Date",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )
                            Text(
                                text = it,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            )
                        }
                        uiState.movieDetail?.runtime?.toString()?.let {
                            Text(
                                text = "Runtime",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )
                            Text(
                                text = it + " mins",
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            )
                        }
                    }
                }

                Text(
                    text = stringResource(Res.string.description),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                uiState.movieDetail?.overview?.let {
                    Text(
                        it,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    )
                }
            }
        }
    }
}