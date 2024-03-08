import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.MyMovieTheme
import ui.MovieListScreen

@Composable
@Preview
fun App() {
    MyMovieTheme {
        MovieListScreen()
    }
}