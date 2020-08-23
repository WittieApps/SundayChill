package wittie.test.sundaychill.domain

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import wittie.test.sundaychill.model.ListOfMoviesResponse
import wittie.test.sundaychill.model.MovieResponse

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val IMAGE_URL = "https://image.tmdb.org/t/p/w780/"
const val API_KEY = "5e58bc24fc9938ffc01414811f7353fa"

interface MoviesAPIRetrofitInterface {

    @GET("{id}")
    suspend fun getMovieById(
        @Path(value = "id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
    ): ListOfMoviesResponse

}