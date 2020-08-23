package wittie.test.sundaychill.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wittie.test.sundaychill.domain.BASE_URL
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            runGetMovieById()
        }

    }

    suspend fun runGetMovieById() {


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()


        val moviesAPIRetrofitInterface = retrofit.create(MoviesAPIRetrofitInterface::class.java)


        val movie550 = moviesAPIRetrofitInterface.getMovieById(550)

    }

}