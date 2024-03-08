object Constant {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNjExODQxNGU4OTVhNWE0NzA5ODg4MzIzNDRmY2QwZCIsInN1YiI6IjU1ZmZhNDQ1OTI1MTQxNDM3NjAwMDlhNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CRpYjmXWbHKbWKkf_3ig-WBP8-8c7v1zvrKeBwjm2Z4"

    //https://image.tmdb.org/t/p/original/wwemzKWzjKYJFfCeiB57q3r4Bcm.png
    //https://image.tmdb.org/t/p/w500/wwemzKWzjKYJFfCeiB57q3r4Bcm.png
    const val originalImageUrl = "https://image.tmdb.org/t/p/original"
    const val w500ImageUrl = "https://image.tmdb.org/t/p/w500"

    enum class MOVIE_LIST {
        TOP_RATED,
        POPULAR,
        UPCOMING
    }
}