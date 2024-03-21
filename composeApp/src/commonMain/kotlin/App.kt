import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.MyMovieTheme
import ui.list.MovieListScreen

@Composable
@Preview
fun App() {
    MyMovieTheme {
        Navigator(screen = MovieListScreen()) {
            SlideTransition(navigator = it)
        }
    }
}