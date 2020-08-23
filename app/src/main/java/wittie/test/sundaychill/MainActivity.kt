package wittie.test.sundaychill

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit


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
            //.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()


        val moviesAPIRetrofitInterface = retrofit.create(MoviesAPIRetrofitInterface::class.java)


        val movie550 = moviesAPIRetrofitInterface.getMovieById(550)

        Log.d("HOLA", movie550.string() ?: "null")
    }

}