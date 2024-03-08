package ui

import utils.Constant.w500ImageUrl
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = getViewModel(Unit, viewModelFactory { MovieListViewModel() }),
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(uiState.movieList != null) {
        LazyVerticalGrid(modifier = Modifier.padding(8.dp),
            columns = GridCells.Adaptive(120.dp),
            content = {
                uiState.movieList?.let {
                    items(it.results) { movie ->
                        Column(modifier = Modifier.padding(4.dp)) {
                            KamelImage(
                                asyncPainterResource(data = w500ImageUrl + movie.posterPath),
                                contentDescription = movie.title,
                            )
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(vertical = 4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                }
            })
    }

}