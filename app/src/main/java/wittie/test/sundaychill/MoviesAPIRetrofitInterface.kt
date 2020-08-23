package wittie.test.sundaychill

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

val BASE_URL = "https://api.themoviedb.org/3/movie/"
val API_KEY = "5e58bc24fc9938ffc01414811f7353fa"

interface MoviesAPIRetrofitInterface {

    @GET("{id}?api_key=5e58bc24fc9938ffc01414811f7353fa")
    suspend fun getMovieById(@Path(value = "id") movieId: Int): ResponseBody

}